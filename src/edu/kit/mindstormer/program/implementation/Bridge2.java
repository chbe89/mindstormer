package edu.kit.mindstormer.program.implementation;

import java.io.IOException;

import edu.kit.mindstormer.Constants;
import edu.kit.mindstormer.com.ComModule;
import edu.kit.mindstormer.com.Communication;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.program.OperatingSystem;
import edu.kit.mindstormer.sensor.Sensor;
import edu.kit.mindstormer.sensor.Sensor.ColorMode;
import lejos.hardware.Sound;
import lejos.utility.Delay;

public class Bridge2 extends AbstractProgram {

	private final ComModule com = Communication.getModule();

	private static final int SPEED = 32;
	private static final int SPEEDCURVE = 38;
	private static final int SENSOR_ROTATION = 75;
	private static final int ROTATION_SPEED = 30;

	private static final int GAP_LIVES = 7;

	private float distanceSample;
	private float colorSample;

	private long time;

	public void run() {

		boolean isStartPhase = true;

		Movement.moveDistance(-10, SPEED);
		State.waitForMovementMotors();

		Movement.rotate(180, ROTATION_SPEED);
		State.waitForMovementMotors();
		
		Movement.rotateSensorMotor(SENSOR_ROTATION);
		Movement.moveDistance(-50, SPEED);
		State.waitForMovementMotors();	
		State.waitForSensorMotor();

		time = System.currentTimeMillis();
		search_line: while (!quit.get()) {
			distanceSample = Sensor.sampleDistance();

			if (isStartPhase) {
				long timeDiff = System.currentTimeMillis() - time;

				if (timeDiff > 15_000) {
					isStartPhase = false;
					OperatingSystem.displayText("Start phase finished");
				}
			}

			Movement.move(false, SPEEDCURVE, false, SPEEDCURVE - (SPEEDCURVE / 3.7f));
			int i = 0;
			int chancesForLine = 0;

			while (i < 1_200 && chancesForLine < GAP_LIVES) {
				distanceSample = Sensor.sampleDistance();
				if (distanceSample >= 8) {
					i++;
				} else {
					i = 0;
				}

				if (!isStartPhase) {
					colorSample = Sensor.sampleColor();
					if (colorSample >= Constants.WOOD_COLOR_THRESHOLD) {
						chancesForLine++;
					} else
						chancesForLine = 0;
				}
			}

			Movement.stop();

			if (chancesForLine >= GAP_LIVES) {
				OperatingSystem.displayText("Found elevator's line border!");
				Sound.beep();
				break search_line;
			}

			Movement.rotate(20, 20);
			while (!State.stopped(true, true)) {
			}
		}

		Movement.stop();

		// correct position
		checkForEdge();

		checkElevatorStatus();
		Movement.rotate(180, ROTATION_SPEED);
		requestElevator();
		State.waitForMovementMotors();

		waitForColorSignal();
		positionInElevator();
		sendElevatorDown();
		Movement.moveDistance(-3, 10);
		Delay.msDelay(1400);
		Movement.moveDistance(-3, 10);
		Delay.msDelay(4000);
		Movement.moveDistance(-15, 10);
		State.waitForMovementMotors();

		Movement.moveDistance(35, SPEED);
		State.waitForMovementMotors();

		Movement.moveCircle(360, true, 22, 15);
		while (!State.stopped(true, true)) {
			if (isSilver(Sensor.sampleColor())) {
				Movement.stop();
			}
		}

		// Call quit, if program terminated successfully (in order to restore
		// state)

		// positionInElevator();
	}

	private void checkForEdge() {
		Sound.beepSequenceUp();
		Movement.moveDistance(-15, 20);
		boolean droveBack = false;
		while (!State.stopped(true, true)) {
			distanceSample = Sensor.sampleDistance();

			if (distanceSample > 8f) {
				Movement.stop();
				Movement.moveDistance(8, 20);
				droveBack = true;
				State.waitForMovementMotors();
				Sound.beep();
				Movement.rotate(20, 20);
				State.waitForMovementMotors();
				break;
			}
		}

		Movement.rotateSensorMotor(-SENSOR_ROTATION);
		if (!droveBack) {
			Movement.moveDistance(8, 20);
			State.waitForMovementMotors();
		}
		
		Movement.moveDistance(6, 20);
		State.waitForSensorMotor();
	}

	private void sendElevatorDown() {
		try {
			while (!com.moveElevatorDown() && !quit.get()) {
				Delay.msDelay(330);
			}
			OperatingSystem.displayText("Send elevator down");
		} catch (IOException e) {
			// do nothing
		}
	}

	private void positionInElevator() {
		boolean firstTime = true;
		boolean needsCorrection = true;
		while (needsCorrection) {
			if (firstTime) {
				Movement.moveDistance(24, 20);
				firstTime = false;
			} else {
				Movement.moveDistance(22, 20);
			}

			boolean leftTouch = false;
			boolean rightTouch = false;
			while (!State.stopped(true, true)) {
				if (Sensor.sampleTouchLeft()) {
					if (!Sensor.sampleTouchRight()) {
						Movement.stop();
						leftTouch = true;
					}
						
				} else if (Sensor.sampleTouchRight()) {
					if (!Sensor.sampleTouchLeft()) {
						Movement.stop();
						rightTouch = true;
					}	
				}
			}

			if (leftTouch) {
				Movement.moveDistance(-20, 10);
				State.waitForMovementMotors();
				Movement.rotate(21, 10);
				State.waitForMovementMotors();

			} else if (rightTouch) {
				Movement.moveDistance(-20, 10);
				State.waitForMovementMotors();
				Movement.rotate(-21, 10);
				State.waitForMovementMotors();
			} else {
				needsCorrection = false;
			}
		}

		Movement.move(true, SPEED);
		while (!Sensor.sampleTouchBoth())
			;
		Movement.stop();

	}

	private void waitForColorSignal() {
		Sensor.setColorMode(ColorMode.AMBIENT);
		
		Sound.beepSequence();

		float color = Sensor.sampleColor();
		for (int i = 0; i < 5; i++)
			color = Sensor.sampleColor();
		Delay.msDelay(300);
		color = Sensor.sampleColor();
		OperatingSystem.displayText("Color: " + color);
		while (color < Constants.ELEVATOR_LIGHT_THRESHOLD) {
			color = Sensor.sampleColor();
			OperatingSystem.displayText("Color: " + color);
			Delay.msDelay(200);
		}
		Sensor.setColorMode(ColorMode.RED);
		color = Sensor.sampleColor();
	}

	private void requestElevator() {
		try {
			while (!com.requestElevator() && !quit.get()) {
				Delay.msDelay(330);
			}
			OperatingSystem.displayText("Requested elevator");
		} catch (IOException e) {
			// do nothing
		}
	}

	private void checkElevatorStatus() {
		try {
			while (!com.requestStatus() && !quit.get()) {
				Delay.msDelay(330);
			}
			OperatingSystem.displayText("Elevator is free. Starting request.");
		} catch (IOException e) {
			// do nothing
		}
	}

	private static boolean isSilver(float sample) {
		return sample >= Constants.LINE_COLOR_THRESHOLD;
	}
}

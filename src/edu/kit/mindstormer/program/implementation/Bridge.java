package edu.kit.mindstormer.program.implementation;

import java.io.IOException;

import lejos.utility.Delay;
import edu.kit.mindstormer.Constants;
import edu.kit.mindstormer.com.ComModule;
import edu.kit.mindstormer.com.Communication;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.program.OperatingSystem;
import edu.kit.mindstormer.sensor.Sensor;
import edu.kit.mindstormer.sensor.Sensor.ColorMode;

public class Bridge extends AbstractProgram {

	private final ComModule com = Communication.getModule();

	private static final int SPEED = 28;
	private static final int SENSOR_ROTATION = 75;
	private static final int ROTATION_SPEED = 30;
	
	private static final int GAP_LIVES = 3;

	private float distanceSample;
	private float colorSample;

	private long time;

	public void run() {
		boolean isStartPhase = true;

		Movement.moveDistance(-10, SPEED);
		State.waitForMovementMotors();
		
		Movement.rotate(180, ROTATION_SPEED);
		State.waitForMovementMotors();

		Movement.moveDistance(-20, SPEED);
		State.waitForMovementMotors();
		
		Movement.rotateSensorMotor(SENSOR_ROTATION);
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

			Movement.move(false, SPEED, false, SPEED - (SPEED / 4.5f));
			int i = 0;
			int chancesForLine = 0;

			while (i < 3 && chancesForLine < GAP_LIVES) {
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
				break search_line;
			}

			Movement.rotate(20, 20);
			while (!State.stopped(true, true)) {
			}
		}

		Movement.stop();
		
		// correct position
		
		Movement.rotateSensorMotor(-SENSOR_ROTATION);
		State.waitForSensorMotor();

		checkElevatorStatus();
		requestElevator();

		Movement.rotate(180, ROTATION_SPEED);
		State.waitForMovementMotors();

		waitForColorSignal();
		Delay.msDelay(3000);
		
		positionInElevator();
		sendElevatorDown();
		Delay.msDelay(5000);
		
		Movement.moveDistance(25, SPEED);
		State.waitForMovementMotors();

		// Call quit, if program terminated successfully (in order to restore
		// state)
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
		Movement.move(true, SPEED);
		while (!Sensor.sampleTouchBoth());
		Movement.stop();
	}

	private void waitForColorSignal() {
		Sensor.setColorMode(ColorMode.AMBIENT);
		float color = Sensor.sampleColor();
		while (color < Constants.ELEVATOR_LIGHT_THRESHOLD){
			color = Sensor.sampleColor();
		}
		Sensor.setColorMode(ColorMode.RED);
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
}

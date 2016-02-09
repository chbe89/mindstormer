package edu.kit.mindstormer.program.implementation;

import lejos.hardware.Button;
import lejos.hardware.Key;
import edu.kit.mindstormer.Constants;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.program.OperatingSystem;
import edu.kit.mindstormer.sensor.Sensor;

public class Bridge extends AbstractProgram {

	private static final int SPEED = -27;
	private static final int SENSOR_ROTATION = 75;

	private float distanceSample;
	private float colorSample;

	private long time;

	public void run() {
		boolean isStartPhase = true;
		boolean arrivedAtElevator = false;

		Movement.rotateSensorMotor(SENSOR_ROTATION);
		while (!State.stoppedSensor())
			;

		time = System.currentTimeMillis();
		search_line: while (!quit.get()) {
			distanceSample = Sensor.sampleDistance();

			if (isStartPhase) {
				long timeDiff = System.currentTimeMillis() - time;

				if (timeDiff > 4_000) {
					isStartPhase = false;
					OperatingSystem.displayText("Start phase finished");
				}
			}

			Movement.move(false, SPEED, false, SPEED - (SPEED / 5));
			int i = 0;
			int chancesForLine = 0;

			while (i < 3 && chancesForLine < 3) {
				distanceSample = Sensor.sampleDistance();
				if (distanceSample >= 8f) {
					i++;
				} else {
					i = 0;
				}

				if (!isStartPhase) {
					colorSample = Sensor.sampleColor();
					OperatingSystem.displayText("COLOR: " + colorSample);
					if (colorSample >= Constants.WOOD_COLOR_THRESHOLD) {
						chancesForLine++;
					} else
						chancesForLine = 0;
				}
			}

			Movement.stop();
			
			if (chancesForLine >= 3) {
				OperatingSystem.displayText("Found elevator's line border!");
				arrivedAtElevator = true;
				break search_line;
			}
			
			Movement.rotate(20, 20);
			while (!State.stopped(true, true)) {
			}
		}

		Movement.stop();
		OperatingSystem.displayText("Setting back.");
		Movement.moveDistance(-25, SPEED);
		State.waitForMovementMotors();

		Movement.stop();
		Movement.rotateSensorMotor(-SENSOR_ROTATION);
		while (!State.stoppedSensor()) {
		}

		if (arrivedAtElevator)
			Button.ESCAPE.simulateEvent(Key.KEY_RELEASED);
	}
}

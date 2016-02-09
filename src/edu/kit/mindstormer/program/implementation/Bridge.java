package edu.kit.mindstormer.program.implementation;

import edu.kit.mindstormer.Constants;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.sensor.Sensor;

public class Bridge extends AbstractProgram {
	private static final int SPEED = -32;
	private static final int SENSOR_ROTATION = 75;

	private float distanceSample;
	private float colorSample;

	private long time;
	
	public void run() {
		boolean isStartPhase = true;
		time = System.currentTimeMillis();

		Movement.rotateSensorMotor(SENSOR_ROTATION);
		while (!State.stoppedSensor()) {
		}

		find_elevator: while (!quit.get()) {
			distanceSample = Sensor.sampleDistance();

			if (isStartPhase) {
				long current = System.currentTimeMillis();
				if (current - time > 4_000)
					isStartPhase = false;
			} else {
				colorSample = Sensor.sampleColor();
				if (colorSample >= Constants.LINE_COLOR_THRESHOLD)
					break find_elevator;
			}

			Movement.move(false, SPEED, false, SPEED - (SPEED / 5));
			int i = 0;
			while (i < 3) {
				distanceSample = Sensor.sampleDistance();
				if (distanceSample >= 8f) {
					i++;
				} else {
					i = 0;
				}
			}
			
			Movement.moveDistance(-25, SPEED);
			State.waitForMovementMotors();
			
			Movement.stop();
			Movement.rotate(20, 20);
			while (!State.stopped(true, true)) {
			}
		}

		Movement.stop();
		Movement.rotateSensorMotor(-SENSOR_ROTATION);
		while (!State.stoppedSensor()) {
		}
	}
}

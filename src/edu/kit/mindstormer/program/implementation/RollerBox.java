package edu.kit.mindstormer.program.implementation;

import lejos.hardware.Button;
import lejos.hardware.Key;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.sensor.Sensor;

public class RollerBox extends AbstractProgram {

	private final int speed = 50;
	private final int rotationSpeed = 30;

	public void run() {
		setBack(10);

		// rotate in order to drive through roller box backwards
		turnAround();

		// drive into roller box
		setBack(20);

		// drive through roller box
		driveThroughBox();

		// drive out of roller box
		driveOut();

		// reposition
		turnAround();

		Button.ESCAPE.simulateEvent(Key.KEY_RELEASED);
	}

	private void driveOut() {
		Movement.moveDistance(-35, speed);
		State.waitForMovementMotors();
	}

	private void driveThroughBox() {
		Movement.move(false, speed);
		while (true)
			if (Sensor.sampleDistance() > 20) {
				Movement.stop();
				break;
			}
	}

	private void turnAround() {
		Movement.rotate(180, rotationSpeed);
		State.waitForMovementMotors();
	}

	private void setBack(int distance) {
		Movement.moveDistance(-distance, speed);
		State.waitForMovementMotors();
	}
}

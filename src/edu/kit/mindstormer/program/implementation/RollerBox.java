package edu.kit.mindstormer.program.implementation;

import lejos.hardware.Button;
import lejos.hardware.Key;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.sensor.Sensor;
import edu.kit.mindstormer.util.HttpLogger;

public class RollerBox extends AbstractProgram {

	private final int speed = 50;
	private final int rotationSpeed = 30;
	
	private static final HttpLogger logger = HttpLogger.getInstance();

	public void run() {
		logger.log("Setting back from roller box (10 cm)");
		setBack(10);

		// rotate in order to drive through roller box backwards
		logger.log("Positioning backwards to roller box");
		turnAround();

		// drive into roller box
		logger.log("Driving into roller box (20 cm)");
		setBack(20);

		// drive through roller box
		logger.log("Start driving through box with fixed distance from wall");
		driveThroughBox();

		// drive out of roller box
		logger.log("Start driving out of roller box (35 cm)");
		driveOut();

		// reposition
		logger.log("Start repositioning robot");
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

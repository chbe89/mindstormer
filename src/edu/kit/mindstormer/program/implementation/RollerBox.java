package edu.kit.mindstormer.program.implementation;

import lejos.hardware.Button;
import lejos.hardware.Key;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.sensor.Sensor;

public class RollerBox extends AbstractProgram {

	private final int speed = 34;
	private final int rotationSpeed = speed / 2;

	public RollerBox() {
		super("RollerBox");
	}

	public void run() {
		// while (!quit.get()) {

		// move till wall is touched
		moveToWall();

		// increase distance from wall
		setBack();

		// rotate in order to drive through roller box backwards
		positionBackwards();

		// drive into roller box
		setBack();

		// drive through roller box
		driveThroughBox();

		// drive out of roller box
		driveOut();

		// reposition
		positionBackwards();

		Button.ESCAPE.simulateEvent(Key.KEY_RELEASED);
	}

	private void driveOut() {
		Movement.moveDistance(-10, speed);
		while (!State.stopped(true, true)) {
		}
	}

	private void driveThroughBox() {
		Movement.move(-speed);
		while (!State.stopped(true, true))
			if (Sensor.sampleDistance() > 20)
				break;
		Movement.stop();
	}

	private void positionBackwards() {
		Movement.rotate(90, rotationSpeed);
		while (!State.stopped(true, true)) {
		}
	}

	private void setBack() {
		Movement.moveDistance(-30, speed);
		while (!State.stopped(true, true)) {
		}
	}

	private void moveToWall() {
		Movement.move(speed);
		while (!State.stopped(true, true))
			if (Sensor.sampleTouchBoth() >= 1)
				break;
		Movement.stop();
	}
}

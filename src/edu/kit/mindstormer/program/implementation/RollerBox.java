package edu.kit.mindstormer.program.implementation;

import lejos.hardware.Button;
import lejos.hardware.Key;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.sensor.Sensor;

public class RollerBox extends AbstractProgram {

	private final int speed = 500;
	private final int rotationSpeed = speed / 2;

	public RollerBox() {
		super("RollerBox");
	}

	public void run() {

		// while (!quit.get()) {

		// move till wall is touched
		Movement.move(speed);
		while (!State.stopped(true, true))
			if (Sensor.sampleTouchBoth() >= 1)
				break;
		Movement.stop();

		// increase distance from wall
		Movement.moveDistance(-30, speed);
		while (!State.stopped(true, true)) {
		}
		
		// rotate in order to drive through roller box backwards
		Movement.rotate(90, rotationSpeed);
		while (!State.stopped(true, true)) {
		}
		
		// drive into roller box
		Movement.moveDistance(-30, speed);
		while (!State.stopped(true, true)) {
		}
		
		// drive through roller box
		Movement.move(-speed);
		while (!State.stopped(true, true))
			if (Sensor.sampleDistance() > 20)
				break;
		Movement.stop();
		
		// drive out of roller box
		Movement.moveDistance(-10, speed);
		while (!State.stopped(true, true)) {
		}
		
		// rotate in order to drive through roller box backwards
		Movement.rotate(90, rotationSpeed);
		while (!State.stopped(true, true)) {
		}
		
		Button.ESCAPE.simulateEvent(Key.KEY_RELEASED);
	}
}

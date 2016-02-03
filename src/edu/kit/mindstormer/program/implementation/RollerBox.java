package edu.kit.mindstormer.program.implementation;

import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;

public class RollerBox extends AbstractProgram {

	private final int speed = 500;
	private final int rotationSpeed = speed / 2;

	public RollerBox() {
		super("RollerBox");
	}

	public void run() {

		while (!quit.get()) {
			Movement.rotate(180, rotationSpeed);
			while(!State.stopped(true, true)) {
			}
			
			Movement.moveDistance(31, speed);
			
			while(!State.stopped(true, true)) {
			}
			
			Movement.rotate(180, rotationSpeed);
			while(!State.stopped(true, true)) {
			}
		}
		//Button.ESCAPE.simulateEvent(Key.KEY_RELEASED);
	}
}

package edu.kit.mindstormer.program.implementation;

import lejos.hardware.Button;
import lejos.hardware.Key;
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

//		while (!quit.get()) {
//			Movement.rotate(180, rotationSpeed, false);
//			Movement.moveDistance(720, speed);
//			Movement.rotate(180, rotationSpeed, false);
//		}
		
		Movement.rotate(180, rotationSpeed, true);
		while(!State.stopped(true, true))
			;
		Movement.stop();
		
		Movement.moveDistance(31, speed);
		
		
		Movement.rotate(180, rotationSpeed, true);
		while(!State.stopped(true, true))
			;
		Movement.stop();
		Button.ESCAPE.simulateEvent(Key.KEY_RELEASED);
	}
}

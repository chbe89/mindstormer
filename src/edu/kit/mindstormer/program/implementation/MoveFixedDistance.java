package edu.kit.mindstormer.program.implementation;

import lejos.hardware.Button;
import lejos.hardware.Key;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.program.AbstractProgram;

public class MoveFixedDistance extends AbstractProgram {

	private final int speed = 500;
	private final float tileWidth = 15.5f;

	@Override
	public void run() {
		Movement.moveDistance(tileWidth * 3, speed);
		Button.ESCAPE.simulateEvent(Key.KEY_RELEASED);
	}

}

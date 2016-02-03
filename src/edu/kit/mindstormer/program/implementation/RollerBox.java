package edu.kit.mindstormer.program.implementation;

import edu.kit.mindstormer.movement.Movement;
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
			Movement.moveDistance(720, rotationSpeed);
			Movement.rotate(180, rotationSpeed);
		}

	}
}

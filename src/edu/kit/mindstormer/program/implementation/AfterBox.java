package edu.kit.mindstormer.program.implementation;

import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;

public class AfterBox extends AbstractProgram {

	@Override
	public void run() {
		Movement.moveCircle(110, false, 10, 15);
		State.waitForMovementMotors();
		Movement.moveDistance(100, 25);
		State.waitForMovementMotors();
		Movement.rotate(10, 10);
		State.waitForMovementMotors();
		
		new Race().startProgram();
		new Endboss().startProgram();
	}

}

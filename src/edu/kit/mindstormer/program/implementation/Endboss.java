package edu.kit.mindstormer.program.implementation;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;

public class Endboss extends AbstractProgram {

	public Endboss() {
		super("Endboss");
	}
	
	public void run() {
		Movement.moveCircle(14, true, 100, 10);
		State.waitForMovementMotors();
		Movement.move(true, 50);
		while(!quit.get());
		Movement.stop();
	}
}

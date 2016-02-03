package edu.kit.mindstormer.program.implementation;
import edu.kit.mindstormer.movement.Complex;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;

public class Seesaw extends AbstractProgram {

	int forwardSpeed = 500;
	
	public Seesaw() {
		super("Seesaw");
	}
	
	public void run() {		
		Complex.driveForwardByDegrees(720, 500);
		while (!State.stopped(true, true)) {
			
		}
		Complex.driveForwardByDegrees(360, 100);
		
	}
}

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
		Complex.driveForwardByDegrees(-600, 5000);
		while (!State.stopped(true, true)) {
			
		}
		Complex.driveForwardByDegrees(-200, 450);
		while (!State.stopped(true, true)) {
			
		}
	}
}

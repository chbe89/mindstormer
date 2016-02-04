package edu.kit.mindstormer.program.implementation;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;

public class Seesaw extends AbstractProgram {

	private int forwardSpeed = 500;
	
	public Seesaw() {
		super("Seesaw");
	}
	
	public void run() {		
		//Movement.driveForwardByDegrees(-600, 5000);
		while (!State.stopped(true, true)) {
			
		}
		//Movement.driveForwardByDegrees(-200, 450);
		while (!State.stopped(true, true)) {
			
		}
	}
}

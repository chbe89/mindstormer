package edu.kit.mindstormer.program.implementation;
import edu.kit.mindstormer.Constants;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.sensor.Sensor;

public class FollowLine extends AbstractProgram {
	float sample;
	int searchAngle = 20;
	int forwardSpeed = 300;
	int turnSpeed = 225;
	
	
	public FollowLine() {
		super("FollowLine");
	}
	
	public void run() {
		int turnMultiplicator = 1;
		boolean turnDirection = true;
		while (!quit.get()) {
			boolean found1 = find((turnDirection ? 1 : -1) * turnMultiplicator * searchAngle);
			boolean found2 = false;
			if (!found1) {
				found2 = find((turnDirection ? -1 : 1) * turnMultiplicator * searchAngle);
			}

			
			if (found1 || found2) {
				turnMultiplicator = 1;
				if (found2) {
					turnDirection = !turnDirection;
				}
				
				Movement.stop();
				Movement.move(forwardSpeed, forwardSpeed);
			    while (sample >= Constants.LINE_COLOR_THRESHOLD) {
			    	sample = Sensor.sampleColor();
			    }
			    Movement.stop();
			    
			    
			} else {

				turnMultiplicator ++;
			}
		}
		Movement.stop();
	}
	
	private boolean find(float angle) {
		Movement.rotate(angle, turnSpeed);
		while (sample < Constants.LINE_COLOR_THRESHOLD && !State.stopped(true, true)) {
			sample = Sensor.sampleColor();
	    }
		if (sample >= Constants.LINE_COLOR_THRESHOLD) {
			Movement.stop();
			return true;
		}
		return false;
	}
}

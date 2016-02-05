package edu.kit.mindstormer.program.implementation;
import edu.kit.mindstormer.Constants;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.sensor.Sensor;

public class FollowLine extends AbstractProgram {
	private float sample;
	private int searchAngle = 25;
	private int forwardSpeed = 17;
	private int turnSpeed = 13;
	private int turnMultiplicator;
	
	
	public FollowLine() {
		super("FollowLine");
	}
	
	public void run() {
		sample = 0f;
		turnMultiplicator = 1;
		
		while (!quit.get()) {
			find();
				
			Movement.move(true, forwardSpeed, true, forwardSpeed);
		    while (sample >= Constants.LINE_COLOR_THRESHOLD) {
		    	sample = Sensor.sampleColor();
		    }
		    Movement.stop();
		}
		Movement.stop();
	}
	
	private void find() {
		boolean found1 = false;
		boolean found2 = false;
		while(!found1 && !found2) {
			found1 = find(turnMultiplicator * searchAngle);
			
			if (!found1) {
				turnMultiplicator += turnMultiplicator > 0 ? 1 : -1;
				found2 = find(-turnMultiplicator * searchAngle);
			}
			turnMultiplicator += turnMultiplicator > 0 ? 1 : -1;
			
		}
		Movement.stop();
		turnMultiplicator = (turnMultiplicator > 0 ? 1 : -1) * (found2 ? -1 : 1);
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

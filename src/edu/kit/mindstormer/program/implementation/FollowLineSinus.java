package edu.kit.mindstormer.program.implementation;
import lejos.hardware.sensor.EV3ColorSensor;
import edu.kit.mindstormer.Constants;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.program.AbstractProgram;


public class FollowLineSinus extends AbstractProgram {
	EV3ColorSensor sensor;
	float[] sample;
	int searchAngle = 20;
	int forwardSpeed = 350;
	int turnSpeed = 200;
	
	
	public FollowLineSinus() {
		super("FollowLineSinus");
	}
	
	public void run() {
		boolean turnDirection = true;
		while (!quit.get()) {
			//Movement.driveCurve(turnDirection, 2000, 300, 1.5f);
			//Delay.msDelay(5000);
			waitForFoundLine();
			
			turnDirection = !turnDirection;
			//Movement.driveCurve(turnDirection, 2000, 300, 1.5f);
			
		    Movement.stop();
		}
	}
	
	private void waitForFoundLine() {
		int counter = 0;
		while (sample[0] >= Constants.LINE_COLOR_THRESHOLD) {
	    	sensor.fetchSample(sample, 0);
	    	if (sample[0] >= Constants.LINE_COLOR_THRESHOLD)
	    		counter++;
	    	else 
	    		counter = 0;
	    }
		counter = 0;
		while (sample[0] < Constants.LINE_COLOR_THRESHOLD) {
	    	sensor.fetchSample(sample, 0);
	    	if (sample[0] < Constants.LINE_COLOR_THRESHOLD)
	    		counter++;
	    	else 
	    		counter = 0;
	    }
	}
}

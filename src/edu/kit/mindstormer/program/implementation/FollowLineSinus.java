package edu.kit.mindstormer.program.implementation;
import lejos.hardware.sensor.EV3ColorSensor;
import edu.kit.mindstormer.Constants;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.sensor.Sensor;

public class FollowLineSinus extends AbstractProgram {
	EV3ColorSensor sensor;
	float[] sample;
	int searchAngle = 20;
	int forwardSpeed = 350;
	int turnSpeed = 200;
	
	
	public FollowLineSinus() {
		super("FollowLineSinus");
		sensor = Sensor.COLOR;
		sample = new float[sensor.sampleSize()];
	}
	
	public void run() {
		boolean turnDirection = true;
		while (!quit.get()) {
			Movement.driveCurve(turnDirection, 2000, 500);
			
			waitForFoundLine();
			turnDirection = !turnDirection;
			Movement.driveCurve(turnDirection, 2000, 500);
			
		    Movement.stop();
		}
	}
	
	private void waitForFoundLine() {
		while (sample[0] >= Constants.LINE_COLOR_THRESHOLD) {
	    	sensor.fetchSample(sample, 0);
	    }
	}
}

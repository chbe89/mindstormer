package edu.kit.mindstormer.program.implementation;
import edu.kit.mindstormer.Constants;
import edu.kit.mindstormer.movement.Complex;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import lejos.hardware.sensor.EV3ColorSensor;

public class FollowLineSinus extends AbstractProgram {
	EV3ColorSensor sensor;
	float[] sample;
	int searchAngle = 20;
	int forwardSpeed = 350;
	int turnSpeed = 200;
	
	
	public FollowLineSinus() {
		super("FollowLine");
		sensor = new EV3ColorSensor(Constants.COLOR_SENSOR_PORT);
		sensor.setCurrentMode("Red");
		sample = new float[sensor.sampleSize()];
	}
	
	public void run() {
		boolean turnDirection = true;
		while (!quit.get()) {
			Complex.driveCurve(turnDirection, 2000, 500);
			
			waitForFoundLine();
			turnDirection = !turnDirection;
			Complex.driveCurve(turnDirection, 2000, 500);
			
		    Movement.stop();
		}
	}
	
	private void waitForFoundLine() {
		while (sample[0] < Constants.LINE_COLOR_THRESHOLD && !State.stopped(true, true)) {
	    	sensor.fetchSample(sample, 0);
	    }
	}
}

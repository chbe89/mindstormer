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
		int turnMultiplicator = 1;
		boolean turnDirection = true;
		while (!quit.get()) {
			Complex.driveCurve(true, 2 ,500);
			
			
			
			boolean found1 = find((turnDirection ? 1 : -1) * turnMultiplicator * searchAngle);
			boolean found2 = false;
			if (!found1) {
				found2 = find((turnDirection ? -1 : 1) * turnMultiplicator * 2 * searchAngle);
			}
			
			if (found1 || found2) {
				turnMultiplicator = 1;
				if (found2) {
					turnDirection = !turnDirection;
				}
			} else {
				turnMultiplicator++;
			}
			
		    
		    Movement.move(forwardSpeed, forwardSpeed);
		    while (sample[0] >= Constants.LINE_COLOR_THRESHOLD) {
		    	sensor.fetchSample(sample, 0);
		    }
		    Movement.stop();
		}
	}
	
	private boolean find(float angle) {
		Complex.rotate(angle, turnSpeed);
		while (sample[0] < Constants.LINE_COLOR_THRESHOLD && !State.stopped(true, true)) {
	    	sensor.fetchSample(sample, 0);
	    }
		if (sample[0] >= Constants.LINE_COLOR_THRESHOLD) {
			Movement.stop();
			return true;
		}
		return false;
	}
}

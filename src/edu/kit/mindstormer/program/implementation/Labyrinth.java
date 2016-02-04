package edu.kit.mindstormer.program.implementation;

import edu.kit.mindstormer.Constants;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.program.OperatingSystem;
import edu.kit.mindstormer.sensor.Sensor;

public class Labyrinth extends AbstractProgram {
	float sampleUltra;
	float sampleTouch;
	final int speed = 22;
//	private float toDrive = 20;
	

	public void run() {

		while (!quit.get()) {
			updateSensors();
			OperatingSystem.displayText("T:" + String.valueOf(sampleTouch) + "U:" + String.valueOf(sampleUltra));

			while (Constants.MIN_WALL_DISTANCE < sampleUltra 
					&& sampleUltra < Constants.MAX_WALL_DISTANCE
					&& sampleTouch != Constants.TOUCH_SENSOR_PRESSED) {
				Movement.holdDistance(speed, 15);
				updateSensors();
				OperatingSystem.displayText("T:" + String.valueOf(sampleTouch) + "U:" + String.valueOf(sampleUltra));
			}
			Movement.stop();
			if (sampleTouch >= Constants.TOUCH_SENSOR_PRESSED) {
				OperatingSystem.displayText("DETECTED TOUCH");
				Movement.moveDistance(1, speed);
				backupAndTurn(true);
			} else if (sampleUltra >= Constants.MAX_WALL_DISTANCE ) {
				OperatingSystem.displayText("DETECTED NO WALL");
				driveCurve90d(false);
			} else if (sampleUltra < Constants.MIN_WALL_DISTANCE) {
				OperatingSystem.displayText("DETECTED TOO CLOSE");
//				Movement.rotate(5, speed);
//				State.waitForStoppedMove();
//				Movement.moveDistance(5, speed);
//				State.waitForStoppedMove();

			} else {
				OperatingSystem.displayText("ERROR UNDEFINED STATE");
			}
		}
	}
	
	private void updateSensors() {
		sampleUltra = Sensor.sampleDistance();
		sampleTouch = Sensor.sampleTouchBoth();
	}
	
	private void backupAndTurn(boolean left) {
		Movement.moveDistance(-15, speed);
		State.waitForStoppedMove();
		Movement.rotate(90 * (left?-1:1), speed);
		State.waitForStoppedMove();
//		Movement.moveDistance(30, speed);
//		State.waitForStoppedMove();
	}
	
	private void driveCurve90d(boolean left) {
//	    	toDrive += sampleUltra;
//		OperatingSystem.displayText("to Drive" + String.valueOf(toDrive));
//		Movement.moveDistance(10, speed);
//		State.waitForStoppedMove();
		Movement.rotate(90 * (left?-1:1), speed);
		State.waitForStoppedMove();
		Movement.moveDistance(55, speed);
		State.waitForStoppedMove();
		OperatingSystem.displayText("Drive Curve Completed");

	}
}

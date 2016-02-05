package edu.kit.mindstormer.program.implementation;

import lejos.utility.Delay;
import edu.kit.mindstormer.Constants;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.program.OperatingSystem;
import edu.kit.mindstormer.sensor.Sensor;

public class Labyrinth extends AbstractProgram {
    //distance to Wall 15 speed 30 trunSpeed 20
    float sampleUltra;
    float sampleTouch;
    final int speed = 35;
    final float turnSpeed = 25;
    final float distanceToWall = 15;

    public void run() {

	while (!quit.get()) {
	    updateSensors();
	    OperatingSystem.displayText("T:" + String.valueOf(sampleTouch) + "U:" + String.valueOf(sampleUltra));

	    while (Constants.MIN_WALL_DISTANCE < sampleUltra && sampleUltra < Constants.MAX_WALL_DISTANCE
		    && sampleTouch != Constants.TOUCH_SENSOR_PRESSED) {
		Movement.holdDistance2(true, speed, distanceToWall);
		updateSensors();
		OperatingSystem.displayText("T:" + String.valueOf(sampleTouch) + "U:" + String.valueOf(sampleUltra));
	    }
	    Movement.stop();
	    if (sampleTouch >= Constants.TOUCH_SENSOR_PRESSED) {
		OperatingSystem.displayText("DETECTED TOUCH");
		Movement.moveDistance(2, speed);
		backupAndTurn(true, false);
	    } else if (sampleUltra >= Constants.MAX_WALL_DISTANCE) {
		OperatingSystem.displayText("DETECTED NO WALL");
		driveCurve90d(false);
	    } else if (sampleUltra < Constants.MIN_WALL_DISTANCE) {
		OperatingSystem.displayText("DETECTED TOO CLOSE");
		backupAndTurn(true, true);
	    } else {
		OperatingSystem.displayText("ERROR UNDEFINED STATE");
	    }
	}
    }

    private void updateSensors() {
	sampleUltra = Sensor.sampleDistance();
	sampleTouch = Sensor.sampleTouchBoth();
    }

    private void backupAndTurn(boolean left, boolean toClose) {
	if (toClose) {
	    Movement.rotate(90 * (left ? 1 : -1), turnSpeed);
	    State.waitForMovementMotors();
	} else {
	    Movement.moveDistance(-distanceToWall, speed);
	    State.waitForMovementMotors();
	    Movement.rotate(90 * (left ? -1 : 1), turnSpeed);
	    State.waitForMovementMotors();
	}
    }

    private void driveCurve90d(boolean left) {
	Movement.moveDistance(-distanceToWall, speed);
	State.waitForMovementMotors();
	//speed 15 / 8
	Movement.move(true, 22.5f, true, 12f);
	updateSensors();
	while(sampleTouch == Constants.TOUCH_SENSOR_UNPRESSED){
		updateSensors();
		OperatingSystem.displayText("Ultra:" + String.valueOf(sampleUltra) + "T:" + String.valueOf(sampleTouch));
		Delay.msDelay(50);
	}
	Movement.stop();
//	Movement.rotate(90 * (left ? -1 : 1), turnSpeed);
//	State.waitForMovementMotors();
//	Movement.moveDistance(45, speed);
//	State.waitForMovementMotors();
	OperatingSystem.displayText("Drive Curve Completed");
    }
}

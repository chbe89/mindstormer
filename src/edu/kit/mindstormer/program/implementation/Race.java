package edu.kit.mindstormer.program.implementation;

import edu.kit.mindstormer.Constants;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.program.OperatingSystem;
import edu.kit.mindstormer.sensor.Sensor;

public class Race extends AbstractProgram {
    private float sampleUltra;
    private boolean sampleTouch;
    private final int speed = 15;
    private final int turnSpeed = 15;

    private float initialDistance = 0;

    public Race() {
	super("Race");
    }

    public void run() {
	sampleUltra = Sensor.sampleDistance();
	initialDistance = sampleUltra;
	sampleTouch = Sensor.sampleTouchBoth();

	while (!quit.get()) {
	    while (Constants.MIN_WALL_DISTANCE < sampleUltra && sampleUltra < (initialDistance + 10.0f) && !sampleTouch) {
		Movement.holdDistance2(true, speed, initialDistance);
		sampleUltra = Sensor.sampleDistance();
		sampleTouch = Sensor.sampleTouchBoth();
	    }
	    Movement.stop();
	    if (sampleTouch) {
		backupAndTurn(false);
	    } else if (sampleUltra < Constants.MIN_WALL_DISTANCE) {
		OperatingSystem.displayText("DETECTED TOO CLOSE");
		Movement.rotate(5, speed);
		State.waitForMovementMotors();
		Movement.moveDistance(5, speed);
		State.waitForMovementMotors();
	    } else {
		OperatingSystem.displayText("DETECTED TOO FAR");
	    }
	}

    }

    private void backupAndTurn(boolean left) {
	Movement.moveDistance(-15, speed);
	State.waitForMovementMotors();
	Movement.rotate(90 * (left ? -1 : 1), turnSpeed);
	State.waitForMovementMotors();
    }

}

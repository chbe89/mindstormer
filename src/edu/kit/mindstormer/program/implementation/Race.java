package edu.kit.mindstormer.program.implementation;

import edu.kit.mindstormer.Constants;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.program.OperatingSystem;
import edu.kit.mindstormer.sensor.Sensor;

public class Race extends AbstractProgram {
    private float sampleUltra;
    private float sampleTouch;
    private final int speed = 28;
    private float initialDistance = 0;

    public Race() {
	super("Race");
	sampleUltra = Sensor.sampleDistance();
	initialDistance = sampleUltra;
	sampleTouch = Sensor.sampleTouchBoth();
    }

    public void run() {

	while (!quit.get()) {
	    Movement.move(true, speed);
	    while (Constants.MIN_WALL_DISTANCE < sampleUltra && sampleUltra < initialDistance
		    && sampleTouch != Constants.TOUCH_SENSOR_PRESSED) {
		sampleUltra = Sensor.sampleDistance();
		sampleTouch = Sensor.sampleTouchBoth();
	    }
	    Movement.stop();
	    if (sampleUltra < Constants.MIN_WALL_DISTANCE) {
		OperatingSystem.displayText("DETECTED TOO CLOSE");
		Movement.rotate(5, speed);
		State.waitForMovementMotors();
		Movement.moveDistance(5, speed);
		State.waitForMovementMotors();
		// Correct angleToWall
	    }
	}

    }

}

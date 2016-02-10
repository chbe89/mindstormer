package edu.kit.mindstormer.program.implementation;

import lejos.utility.Delay;
import edu.kit.mindstormer.Constants;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.program.OperatingSystem;
import edu.kit.mindstormer.sensor.Sensor;

public class Race extends AbstractProgram {
    private float sampleUltra;
    private boolean sampleTouch;
    private final int speed = 40;
    private final int turnSpeed = 30;
    private int counterWallEnd = 0;

    private float initialDistance = 0;
    boolean stopRace = false;

    public Race() {
	super("Race");
    }

    @Override
    public void initialize() {
	super.initialize();
	stopRace = false;
	initialDistance = 0;
	counterWallEnd = 0;
    }

    public void run() {
	sampleUltra = Sensor.sampleDistance();
	initialDistance = sampleUltra;
	sampleTouch = Sensor.sampleTouchBoth();

	while (!quit.get() && !stopRace) {
	    while (Constants.MIN_WALL_DISTANCE < sampleUltra && sampleUltra < (initialDistance + 10.0f) && !sampleTouch) {
		Movement.holdDistance2(true, speed, initialDistance);
		sampleUltra = Sensor.sampleDistance();
		sampleTouch = Sensor.sampleTouchBoth();
	    }
	    Movement.stop();
	    if (sampleTouch) {
		Delay.msDelay(5000);
	    } else if (sampleUltra < Constants.MIN_WALL_DISTANCE) {
		OperatingSystem.displayText("DETECTED TOO CLOSE");
		Movement.rotate(5, speed);
		State.waitForMovementMotors();
		Movement.moveDistance(5, speed);
		State.waitForMovementMotors();
	    } else if (sampleUltra >= Constants.MAX_WALL_DISTANCE) {
		counterWallEnd++;
		if (counterWallEnd >= 3) {
		    Movement.moveCircle(90, true, 15, turnSpeed);
		    State.waitForMovementMotors();
		    stopRace = true;
		}
	    } else {
		OperatingSystem.displayText("DETECTED TOO FAR");
	    }
	}

	Movement.move(true, speed);
	while (!Sensor.sampleTouch());

	backupAndTurn();

	Movement.move(true, speed);

	while (Sensor.sampleDistance() > Constants.MAX_WALL_DISTANCE);

	Movement.stop();

    }

    private void backupAndTurn() {
	Movement.moveCircle(-90, false, 10, 10);
	State.waitForMovementMotors();
    }

}

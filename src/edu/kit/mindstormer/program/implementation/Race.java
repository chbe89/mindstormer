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
			while (Constants.MIN_WALL_DISTANCE < sampleUltra && !sampleTouch) {
				
				if (sampleUltra < Constants.WALL_DISTANCE_RACE) {
					Movement.move(true, speed);
				} else {
					Movement.holdDistance2(true, speed, 45);
				}
				sampleUltra = Sensor.sampleDistance();
				sampleTouch = Sensor.sampleTouchBoth();
				OperatingSystem.displayText("U: " + sampleUltra);
			}
			
			if (sampleTouch) {
				Movement.stop();
				Movement.moveCircle(-90, false, 25, turnSpeed);
				State.waitForMovementMotors();
				stopRace = true;
			} 
			
			if (sampleUltra < Constants.MIN_WALL_DISTANCE) {
				OperatingSystem.displayText("DETECTED TOO CLOSE");
				Movement.rotate(5, speed);
				State.waitForMovementMotors();
				Movement.moveDistance(5, speed);
				State.waitForMovementMotors();
			}
		}

		Movement.move(true, speed);
		while (!Sensor.sampleTouch())
			;

		backupAndTurn();

		Movement.move(true, speed);

		while (Sensor.sampleDistance() > Constants.MAX_WALL_DISTANCE)
			;

		Movement.stop();

	}

	private void backupAndTurn() {
		Movement.moveCircle(-90, false, 10, 10);
		State.waitForMovementMotors();
	}

}

package edu.kit.mindstormer.program.implementation;
import edu.kit.mindstormer.Constants;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.program.OperatingSystem;
import edu.kit.mindstormer.sensor.Sensor;
import lejos.hardware.Sound;
import lejos.utility.Delay;

public class Seesaw extends AbstractProgram {

	private float sample;
	// private int searchAngle = 25;
	private int forwardSpeed = 17;
	private int turnSpeed = 13;
	private int turnMultiplicator;

	private int[] searchAngles = { 30, 60, 120, 180, 120, 30 };

	private boolean foundInFirstDirection = false;
	private boolean foundInSecondDirection = false;

	private int barcodeTimer = 10000; // 90000;
	
	@Override
	public void initialize() {
		super.initialize();
		sample = 0f;
		turnMultiplicator = 1;
	}
	
	public void run() {		

		boolean onLine = true;
		long startTime = System.currentTimeMillis();
		long elapsedTime = 0;
		while (!quit.get() && onLine) {
			sample = Sensor.sampleColor();
			
			if (sample < Constants.LINE_COLOR_THRESHOLD) {
				elapsedTime = System.currentTimeMillis() - startTime;
				if (elapsedTime > barcodeTimer) {
					break;
				} else {
					// normal linesearch
					onLine = searchLine();
				}
			}

			if (onLine)
				moveAlongLine();
		}
		Sound.beepSequenceUp();
		Movement.move(true, 30);
		while(!Sensor.sampleTouchBoth());
		Movement.stop();
		
		Sound.beep();
		State.waitForMovementMotors();
		//Delay.msDelay(3000);
		Movement.moveDistance(-15, 20);
	    State.waitForMovementMotors();
	    Movement.rotate(90 , turnSpeed);
	    State.waitForMovementMotors();
	    Movement.moveDistance(200, 30);
	    State.waitForMovementMotors();
	}

	private void moveAlongLine() {
		OperatingSystem.displayText("Moving along line");
		Movement.move(true, forwardSpeed, true, forwardSpeed);
		while (sample >= Constants.LINE_COLOR_THRESHOLD) {
			sample = Sensor.sampleColor();
		}
		Movement.stop();
	}

	private boolean searchLine() {
		OperatingSystem.displayText("Searching for line");
		foundInFirstDirection = false;
		foundInSecondDirection = false;

		boolean keepSearching = !foundInFirstDirection && !foundInSecondDirection;
		for (int i = 0; i < searchAngles.length && keepSearching; i++) {
			foundInFirstDirection = searchByAngle(turnMultiplicator * searchAngles[i]);

			if (!foundInFirstDirection) {
				i++;
				foundInSecondDirection = searchByAngle(-turnMultiplicator * searchAngles[i]);
			}

			keepSearching = !foundInFirstDirection && !foundInSecondDirection;
		}

		State.waitForMovementMotors();
		OperatingSystem.displayText("Found line in direction: " + directionToString());
		Movement.stop();
		resetSearchRange();

		return foundInFirstDirection || foundInSecondDirection;
	}

	private void resetSearchRange() {
		turnMultiplicator = (turnMultiplicator > 0 ? 1 : -1) * (foundInSecondDirection ? -1 : 1);
	}

	// private void increaseSearchRange() {
	// turnMultiplicator += turnMultiplicator > 0 ? 1 : -1;
	// }

	private boolean searchByAngle(float angle) {
		Movement.rotate(angle, turnSpeed);
		while (sample < Constants.LINE_COLOR_THRESHOLD && !State.stopped(true, true)) {
			sample = Sensor.sampleColor();
		}
		if (sample >= Constants.LINE_COLOR_THRESHOLD) {
			Movement.stop();
			return true;
		}
		return false;
	}

	private String directionToString() {
		if (turnMultiplicator > 0)
			return "LEFT";
		return "RIGHT";
	}
}

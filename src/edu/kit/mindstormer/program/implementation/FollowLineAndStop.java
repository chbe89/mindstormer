package edu.kit.mindstormer.program.implementation;

import lejos.hardware.Button;
import lejos.hardware.Key;
import edu.kit.mindstormer.Constants;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.program.OperatingSystem;
import edu.kit.mindstormer.sensor.Sensor;

public class FollowLineAndStop extends AbstractProgram {
	private float sample;
	// private int searchAngle = 25;
	private int forwardSpeed = 17;
	private int turnSpeed = 13;
	private int turnMultiplicator;

	// private int[] searchAngles = {45, 145, 200, 100};
	private int[] searchAngles = { 30, 60, 90, 120, 180, 240 };

	private boolean foundInFirstDirection = false;
	private boolean foundInSecondDirection = false;

	@Override
	public void initialize() {
		super.initialize();
		sample = 0f;
		turnMultiplicator = 1;
	}

	@Override
	public void run() {
		boolean onLine = true;
		while (!quit.get() && onLine) {
			onLine = searchLine();
			if (onLine)
				moveAlongLine();
			else {
				OperatingSystem.displayText("Didn't find line. Correcting angle");
				// correct position
				Button.ESCAPE.simulateEvent(Key.KEY_RELEASED);
			}
		}
	}

	@Override
	public void terminate() {
		super.terminate();
		Movement.stop();
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

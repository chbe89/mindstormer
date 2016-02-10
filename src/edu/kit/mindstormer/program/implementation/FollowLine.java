package edu.kit.mindstormer.program.implementation;

import edu.kit.mindstormer.Constants;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.sensor.Sensor;
import lejos.hardware.Sound;

public class FollowLine extends AbstractProgram {
	
	private final int searchAngle = 25;
	private final int forwardSpeed = 17;
	private final int turnSpeed = 13;
	private float sample;
	private int turnMultiplicator;

	private boolean foundInFirstDirection = false;
	private boolean foundInSecondDirection = false;

	@Override
	public void initialize() {
		super.initialize();
		sample = 0f;
		turnMultiplicator = 1;
	}

	public void run() {
		long startTime = System.currentTimeMillis();
		long elapsedTime = 0;
		boolean onLine = true;
		while (!quit.get() && elapsedTime < 120_000 && onLine) {
			elapsedTime = System.currentTimeMillis() - startTime;
			onLine = searchLine();
			moveAlongLine();
		}
		Sound.beepSequenceUp();
		Movement.stop();
	}

	private void moveAlongLine() {
		Movement.move(true, forwardSpeed, true, forwardSpeed);
		while (sample >= Constants.LINE_COLOR_THRESHOLD) {
			sample = Sensor.sampleColor();
		}
		Movement.stop();
	}

	private boolean searchLine() {
		foundInFirstDirection = false;
		foundInSecondDirection = false;

		boolean keepSearching = !foundInFirstDirection && !foundInSecondDirection;
		while (keepSearching) {
			foundInFirstDirection = searchByAngle(turnMultiplicator * searchAngle);

			if (!foundInFirstDirection) {
				increaseSearchRange();
				foundInSecondDirection = searchByAngle(-turnMultiplicator * searchAngle);
			}
			increaseSearchRange();
		}

		Movement.stop();
		resetSearchRange();
		return foundInFirstDirection || foundInSecondDirection;
	}

	private void resetSearchRange() {
		turnMultiplicator = (turnMultiplicator > 0 ? 1 : -1) * (foundInSecondDirection ? -1 : 1);
	}

	private void increaseSearchRange() {
		turnMultiplicator += turnMultiplicator > 0 ? 1 : -1;
	}

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
}
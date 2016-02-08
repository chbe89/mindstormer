package edu.kit.mindstormer.program.implementation;

import edu.kit.mindstormer.Constants;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.sensor.Sensor;

public class FollowLine extends AbstractProgram {
	private float sample;
	private int searchAngle = 25;
	private int forwardSpeed = 17;
	private int turnSpeed = 13;
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
		while (!quit.get()) {
			searchLine();
			moveAlongLine();
		}

	}

	@Override
	public void terminate() {
		super.terminate();
		Movement.stop();
	}

	private void moveAlongLine() {
		Movement.move(true, forwardSpeed, true, forwardSpeed);
		while (sample >= Constants.LINE_COLOR_THRESHOLD) {
			sample = Sensor.sampleColor();
		}
		Movement.stop();
	}

	private void searchLine() {
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

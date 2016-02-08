package edu.kit.mindstormer.program.implementation;

import edu.kit.mindstormer.Constants;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.sensor.Sensor;

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
		while (!quit.get()) {
			long elapsedTime = System.currentTimeMillis() - startTime;
			if (elapsedTime > 500) {
				if (searchQRCode() > 0) 
					break;
				else 
					searchLine();
			}
			else {
				searchLine();
			}
			moveAlongLine();
		}

	}

	@Override
	public void terminate() {
		super.terminate();
		Movement.stop();
	}
	
	private int searchQRCode() {
		Movement.moveDistance(7, 30);
		boolean qrFound = false;
		while(!qrFound) {
			Sensor.sampleColor();
			if (sample >= Constants.LINE_COLOR_THRESHOLD)
				qrFound = true;
			if (State.stopped(true, true))
				break;
		}
		int qrNr = 0;
		if (qrFound) {
			qrNr = 1;
			while (sample >= Constants.LINE_COLOR_THRESHOLD) {
				State.waitForMovementMotors();
				Movement.moveDistance(2.5f, 30);
				State.waitForMovementMotors();
				Sensor.sampleColor();
				qrNr++;
			}
		} else {
			State.waitForMovementMotors();
			Movement.moveDistance(-7, 30);
		}
		State.waitForMovementMotors();
		return qrNr;
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

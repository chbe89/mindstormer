package edu.kit.mindstormer.program.implementation;

import lejos.hardware.Button;
import lejos.hardware.Key;
import edu.kit.mindstormer.Constants;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.program.OperatingSystem;
import edu.kit.mindstormer.sensor.Sensor;

public class FollowLineLiftToSeesaw extends AbstractProgram {
	private float sample;
	// private int searchAngle = 25;
	private int forwardSpeed = 17;
	private int turnSpeed = 13;
	private int turnMultiplicator;

	private int[] searchAngles = {30, 60, 120, 180, 120, 30};
	//private int[] searchAngles = { 30, 60, 90, 120, 180, 240 };
	private int[] miniSearchAngles = { 30, 60, 90 };

	private boolean foundInFirstDirection = false;
	private boolean foundInSecondDirection = false;

	private int barcodeTimer = 1; // 90000;

	@Override
	public void initialize() {
		super.initialize();
		sample = 0f;
		turnMultiplicator = 1;
	}

	@Override
	public void run() {
		boolean onLine = true;
		long startTime = System.currentTimeMillis();
		while (!quit.get() && onLine) {
			long elapsedTime = System.currentTimeMillis() - startTime;
			sample = Sensor.sampleColor();
			if (sample < Constants.LINE_COLOR_THRESHOLD) {
				if (elapsedTime > barcodeTimer) {
					Movement.moveDistance(3, 15);
					State.waitForMovementMotors();
					onLine = searchLine();
					
					if (!onLine) {
						Movement.moveDistance(-3, 15);
						State.waitForMovementMotors();
						Movement.move(true, 35);
						while (!Sensor.sampleTouchBoth());
						
						OperatingSystem.displayText("Wall TOUCHED + CIRCLE");
						Movement.moveCircle(-90, false, 25, 15);
						State.waitForMovementMotors();
						OperatingSystem.displayText("CIRCLE COMPLETE");
						return;
					}
				} else {
					onLine = searchLine();
				}
				
				/*
				 * Movement.moveDistance(10, 20); State.waitForMovementMotors();
				 * float wallDistance = Sensor.sampleDistance();
				 * OperatingSystem.displayText("Wall Distance: " +
				 * wallDistance);
				 * 
				 * //Delay.msDelay(1000); if (wallDistance > 35 && wallDistance
				 * < 45) {
				 * 
				 * Movement.alignParallel(wallDistance, 15);
				 * State.waitForMovementMotors(); Movement.move(true, 20); while
				 * (!Sensor.sampleTouchBoth()) {
				 * 
				 * } Movement.stop(); return; // Seesaw done } else {
				 * Movement.moveDistance(-10, 20);
				 * State.waitForMovementMotors(); onLine = searchLine(); }
				 */
			}

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

	private int searchBarcode() {
		OperatingSystem.displayText("Searching BarCode");
		Movement.moveDistance(11, 30);
		State.waitForMovementMotors();
		boolean qrFound = false;
		while (!qrFound) {
			sample = Sensor.sampleColor();
			if (sample >= Constants.LINE_COLOR_THRESHOLD) {
				OperatingSystem.displayText("BarCode Found!");
				qrFound = true;
			} else if (State.stopped(true, true)) {
				OperatingSystem.displayText("BarCode NOT Found!");
				break;
			}
		}
		int qrNr = 0;
		if (qrFound) {
			qrNr = scanBarcodeWhileDriving();
		} else {
			// drive back
			State.waitForMovementMotors();
			Movement.moveDistance(-11, 30);
		}
		State.waitForMovementMotors();
		return qrNr;
	}

	private int scanBarcodeWhileDriving() {
		int qrNr = 0;
		boolean black = true;
		boolean silver = true;
		while (silver && black) {
			qrNr++;
			OperatingSystem.displayText("BarCode " + qrNr + " Found!");
			State.waitForMovementMotors();
			black = false;
			silver = false;
			Movement.moveDistance(6.0f, 20);
			while (!State.stopped(true, true)) {
				sample = Sensor.sampleColor();
				if (isBlack(sample))
					black = true;
				if (black && isSilver(sample)) {
					silver = true;
					// Movement.stop();
				}
			}
		}

		// Movement.moveDistance(7.5f, 20);
		State.waitForMovementMotors();
		return qrNr;
	}

	/*
	 * private int scanBarcodeWhileDriving2() { int qrNr = 0; boolean black =
	 * true; boolean silver = true; while (silver && black) { qrNr++;
	 * OperatingSystem.displayText("BarCode " + qrNr + " Found!");
	 * State.waitForMovementMotors(); Movement.moveDistance(6.0f, 30); black =
	 * false; silver = false; while (!State.stopped(true, true)) { sample =
	 * Sensor.sampleColor(); if (sample < Constants.LINE_COLOR_THRESHOLD) black
	 * = true; if (black && sample >= Constants.LINE_COLOR_THRESHOLD) { silver =
	 * true; Movement.stop(); } } sample = Sensor.sampleColor(); }
	 * 
	 * Movement.moveDistance(7.5f, 30); State.waitForMovementMotors(); return
	 * qrNr; }
	 */
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

	private boolean miniSearchLine() {
		OperatingSystem.displayText("miniSearchLine");
		foundInFirstDirection = false;
		foundInSecondDirection = false;

		boolean keepSearching = !foundInFirstDirection && !foundInSecondDirection;
		for (int i = 0; i < miniSearchAngles.length && keepSearching; i++) {
			foundInFirstDirection = searchByAngle(turnMultiplicator * miniSearchAngles[i]);

			if (!foundInFirstDirection) {
				i++;
				foundInSecondDirection = searchByAngle(-turnMultiplicator * miniSearchAngles[i]);
			}

			keepSearching = !foundInFirstDirection && !foundInSecondDirection;
		}

		OperatingSystem.displayText("Found line in direction: " + directionToString());
		Movement.stop();
		resetSearchRange();
		if (!(foundInFirstDirection || foundInSecondDirection)) {
			Movement.rotate(turnMultiplicator * miniSearchAngles[miniSearchAngles.length - 1], turnSpeed);
			State.waitForMovementMotors();
		}
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

	public static boolean searchLineAfter() {
		boolean found = false;
		int speed = 30;
		int radius = 9;
		OperatingSystem.displayText("Suche Rechtsbogen");

		Movement.rotate(-20, 20);
		State.waitForMovementMotors();
		Movement.moveCircle(180, true, radius, 20);

		while (isBlack(Sensor.sampleColor()) && !State.stopped(true, true)) {
		}
		if (isSilver(Sensor.sampleColor()))
			found = true;
		Movement.stop();

		if (!found) {
			OperatingSystem.displayText("Suche Linksbogen");
			Movement.moveCircle(-180, true, radius, 20);
			State.waitForMovementMotors();
			Movement.rotate(40, 20);
			State.waitForMovementMotors();
			Movement.moveCircle(180, false, radius, 20);
			while (isBlack(Sensor.sampleColor()) && !State.stopped(true, true)) {
			}
			if (isSilver(Sensor.sampleColor()))
				found = true;
			Movement.stop();
		}

		if (!found) {
			OperatingSystem.displayText("Suche Gradeaus");
			Movement.moveCircle(-180, true, radius, 20);
			State.waitForMovementMotors();
			Movement.rotate(-20, 20);
			State.waitForMovementMotors();
			Movement.moveDistance(60, 30);
			while (isBlack(Sensor.sampleColor()) && !State.stopped(true, true)) {
			}
			if (isSilver(Sensor.sampleColor()))
				found = true;
		}

		return found;
	}

	private static boolean isBlack(float sample) {
		return sample < Constants.LINE_COLOR_THRESHOLD;
	}

	private static boolean isSilver(float sample) {
		return sample >= Constants.LINE_COLOR_THRESHOLD;
	}

}

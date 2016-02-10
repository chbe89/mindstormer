package edu.kit.mindstormer.program.implementation;

import lejos.hardware.Sound;
import lejos.utility.Delay;
import edu.kit.mindstormer.Constants;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.program.OperatingSystem;
import edu.kit.mindstormer.sensor.Sensor;

public class Labyrinth extends AbstractProgram {
	// distance to Wall 15 speed 30 trunSpeed 20
	float sampleUltra;
	float sampleLine;
	boolean sampleTouch;
	final int speed = 35;
	final float turnSpeed = 25;
	final float distanceToWall = 15;
	boolean silverLineFound = false;
	int counterLabEnd = 0;
	int endLabyrinth = 0;

	public void run() {
		OperatingSystem.displayText("E:" + counterLabEnd);

		while (!quit.get() && (endLabyrinth < 3)) {
			updateSensors();
			OperatingSystem.displayText("T:" + String.valueOf(sampleTouch) + "U:" + String.valueOf(sampleUltra));

			while (Constants.MIN_WALL_DISTANCE < sampleUltra && sampleUltra < Constants.MAX_WALL_DISTANCE
					&& !sampleTouch) {
				Movement.holdDistance2(true, speed, distanceToWall);
				updateSensors();
				OperatingSystem.displayText("T:" + String.valueOf(sampleTouch) + "U:" + String.valueOf(sampleUltra));
			}
			Movement.stop();
			if (sampleTouch) {
				OperatingSystem.displayText("DETECTED TOUCH");
				Movement.moveDistance(15, speed);
				backupAndTurn(true, false);
			} else if (sampleUltra >= Constants.MAX_WALL_DISTANCE) {
				OperatingSystem.displayText("DETECTED NO WALL");
				driveCurve90d(false);
			} else if (sampleUltra <= Constants.MIN_WALL_DISTANCE) {
				OperatingSystem.displayText("DETECTED TOO CLOSE");
				backupAndTurn(true, true);
			} else {
				OperatingSystem.displayText("T:" + String.valueOf(sampleTouch) + "L:" + String.valueOf(sampleLine));
				OperatingSystem.displayText("ERROR UNDEFINED STATE");
			}
		}

		Movement.moveDistance(65, speed);
		State.waitForMovementMotors();
	}

	private void updateSensors() {
		sampleUltra = Sensor.sampleDistance();
		sampleTouch = Sensor.sampleTouchBoth();
		sampleLine = Sensor.sampleColor();
	}

	private void backupAndTurn(boolean left, boolean toClose) {
		if (toClose) {
			Movement.rotate(90 * (left ? 1 : -1), turnSpeed);
			State.waitForMovementMotors();
		} else {
			Movement.moveDistance(-distanceToWall, speed);
			State.waitForMovementMotors();
			Movement.rotate(90 * (left ? -1 : 1), turnSpeed);
			State.waitForMovementMotors();
		}
		if (silverLineFound) {
			Sound.beep();
			endLabyrinth++;
		}
	}

	private void driveCurve90d(boolean left) {
		Movement.moveDistance(-distanceToWall, speed);
		State.waitForMovementMotors();
		// speed 15 / 8
		Movement.move(true, 22.5f, true, 12f);
		updateSensors();

		while (!sampleTouch) {
			OperatingSystem.displayText("L: " + String.valueOf(sampleLine) + "T: " + String.valueOf(sampleTouch));
			updateSensors();
			if (sampleLine > Constants.LINE_COLOR_THRESHOLD_LAB) {
				counterLabEnd++;
				if (counterLabEnd >= 3) {
					silverLineFound = true;
					Sound.beepSequenceUp();
				}
			} else {

				counterLabEnd = 0;

			}
		}
		Movement.stop();

	}
}

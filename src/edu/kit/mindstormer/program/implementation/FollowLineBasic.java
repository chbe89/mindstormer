package edu.kit.mindstormer.program.implementation;

import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.OperatingSystem;
import edu.kit.mindstormer.sensor.Sensor;

public class FollowLineBasic {
	private final float[] searchAngles;
	private final float lineColorThreshhold;
	private final float centimetersPerSecond;
	private final float turnCentimetersPerSecond;
	private int turnMultiplicator = 1;
	
	public FollowLineBasic(float[] searchAngles, float lineColorThreshhold, float centimetersPerSecond, float turnCentimetersPerSecond) {
		this.searchAngles = searchAngles;
		this.lineColorThreshhold = lineColorThreshhold;
		this.centimetersPerSecond = centimetersPerSecond;
		this.turnCentimetersPerSecond = turnCentimetersPerSecond;
	}
	
	public void run() {
		boolean lineFound = true;
		while(lineFound) {
			moveAlongLine();
			lineFound = searchLine();
		}
	}
	
	
	public void moveAlongLine() {
		OperatingSystem.displayText("Moving along line");
		Movement.move(true, centimetersPerSecond, true, centimetersPerSecond);
		while (Sensor.sampleColor() >= lineColorThreshhold);
		Movement.stop();
	}
	
	public boolean searchLine() {
		OperatingSystem.displayText("Searching for line");
		boolean foundInFirstDirection = false;
		boolean foundInSecondDirection = false;
		

		boolean keepSearching = !foundInFirstDirection && !foundInSecondDirection;
		for (int i = 0; i < searchAngles.length && keepSearching; i++) {
			foundInFirstDirection = searchByAngle(turnMultiplicator * searchAngles[i]);

			if (!foundInFirstDirection) {
				i++;
				foundInSecondDirection = searchByAngle(-turnMultiplicator * searchAngles[i]);
			}

			keepSearching = !foundInFirstDirection && !foundInSecondDirection;
		}

		Movement.stop();
		turnMultiplicator = (turnMultiplicator > 0 ? 1 : -1) * (foundInSecondDirection ? -1 : 1);
		return foundInFirstDirection || foundInSecondDirection;
	}
	
	private boolean searchByAngle(float angle) {
		float sample = 0;
		Movement.rotate(angle, turnCentimetersPerSecond);
		while (sample < lineColorThreshhold && !State.stopped(true, true)) {
			sample = Sensor.sampleColor();
		}
		if (sample >= lineColorThreshhold) {
			Movement.stop();
			return true;
		}
		return false;
	}
}

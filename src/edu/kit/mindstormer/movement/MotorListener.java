package edu.kit.mindstormer.movement;

import lejos.robotics.RegulatedMotor;
import lejos.robotics.RegulatedMotorListener;

public class MotorListener implements RegulatedMotorListener {
	private boolean stopped = true;
	
	
	@Override
	public void rotationStarted(RegulatedMotor motor, int tachoCount, boolean stalled, long timeStamp) {
		stopped = false;
	}

	@Override
	public void rotationStopped(RegulatedMotor motor, int tachoCount, boolean stalled, long timeStamp) {
		stopped = true;
	}

	public boolean isStopped() {
		return stopped;
	}

}

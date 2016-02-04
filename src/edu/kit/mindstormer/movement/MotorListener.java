package edu.kit.mindstormer.movement;

import lejos.robotics.RegulatedMotor;
import lejos.robotics.RegulatedMotorListener;

public class MotorListener implements RegulatedMotorListener {
	private boolean stoppedLeft = true;
	private boolean stoppedRight = true;
	private boolean stoppedSensor = true;
	
	
	@Override
	public void rotationStarted(RegulatedMotor motor, int tachoCount, boolean stalled, long timeStamp) {
		updateState(motor, false);
	}

	@Override
	public void rotationStopped(RegulatedMotor motor, int tachoCount, boolean stalled, long timeStamp) {
		updateState(motor, true);
	}
	
	private void updateState(RegulatedMotor motor, boolean stopped) {
		if (motor.equals(Movement.leftMotor)) 
			stoppedLeft = stopped;
		else if (motor.equals(Movement.rightMotor)) 
			stoppedRight = stopped;
		else if (motor.equals(Movement.sensorMotor)) 
			stoppedSensor = stopped;
	}

	public boolean isStopped(boolean left, boolean right, boolean sensor) {
		boolean stopped = true;
		if (left) {
			stopped &= stoppedLeft;
		}
		if (right) {
			stopped &= stoppedRight;
		}
		if (sensor) {
			stopped &= stoppedSensor;
		}
		return stopped;
	}

}

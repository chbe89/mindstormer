package edu.kit.mindstormer.movement;

public class State {
	private State() {};
	
	public static boolean stopped(boolean left, boolean right) {
		boolean stopped = true;
		if (left) {
			stopped &= Movement.leftMotorListener.isStopped();
		}
		if (right) {
			stopped &= Movement.rightMotorListener.isStopped();
		}
		return stopped;
	}
	
	public static boolean stoppedSensor() {
		return Movement.sensorMotorListener.isStopped();
	}
	
	public static void waitForStoppedMove() {
		while (!stopped(true, true)) {
		}
	}
}

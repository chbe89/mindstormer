package edu.kit.mindstormer.movement;

public class State {
	private State() {};
	
	public static boolean stopped(boolean left, boolean right) {
		return Movement.motorListener.isStopped(left, right, false);
	}
	
	public static boolean stoppedSensor() {
		return Movement.motorListener.isStopped(false, false, true);
	}
	
	public static void waitForMotors(boolean left, boolean right) {
		while(!stopped(left, right)) {}
	}
	
	public static void waitForSensorMotor() {
		while(!Movement.motorListener.isStopped(false, false, true)) {}
	}
}

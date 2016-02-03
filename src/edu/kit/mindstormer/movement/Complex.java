package edu.kit.mindstormer.movement;

import edu.kit.mindstormer.Constants;

public class Complex {
	
	
	private Complex() {};
	
	public static void driveForwardByDegrees(float wheelTurn, float speed) {
		int motorAngle = getMotorAngle(wheelTurn, true);
    	Movement.stop();
		Movement.moveLeft(motorAngle, speed / 2, true);
		Movement.moveRight(motorAngle, speed / 2, true);
	}
	
	public static void rotate(float angle, float speed) {
		int motorAngle = getMotorAngle(angle, true);
    	Movement.stop();
		Movement.moveLeft(-motorAngle, speed / 2, true);
		Movement.moveRight(motorAngle, speed / 2, true);
	}
	
	public static void rotateLeft(float angle, float speed) {
		int motorAngle = getMotorAngle(angle, false);
    	Movement.stop();
    	Movement.moveLeft(motorAngle, speed, true);
	}
	
	public static void rotateRight(float angle, float speed) {
		int motorAngle = getMotorAngle(angle, false);
    	Movement.stop();
    	Movement.moveRight(motorAngle, speed, true);
	}
	
	private static int getMotorAngle(float angle, boolean bothWheels) {
		return Math.round(Constants.ROTATION_FACTOR * angle * (bothWheels ? 0.5f : 1));
	}
}

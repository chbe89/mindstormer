package edu.kit.mindstormer.movement;

import edu.kit.mindstormer.Constants;

public class Complex {
	
	
	private Complex() {};
	
		public static void rotate(float angle, int speed) {
		int motorAngle = getMotorAngle(angle, true);
		Movement.leftWheel.startSynchronization();
		Movement.moveLeft(-motorAngle, speed / 2, true);
		Movement.moveRight(motorAngle, speed / 2, true);
		Movement.leftWheel.endSynchronization();;
	}
	
	
	public static void rotateLeft(float angle, int speed) {
		int motorAngle = getMotorAngle(angle, false);
    	Movement.stop();
    	Movement.moveLeft(motorAngle, speed, true);
	}
	
	public static void rotateRight(float angle, int speed) {
		int motorAngle = getMotorAngle(angle, false);
    	Movement.stop();
    	Movement.moveRight(motorAngle, speed, true);
	}
	
	private static int getMotorAngle(float angle, boolean bothWheels) {
		return Math.round(Constants.ROTATION_FACTOR * angle * (bothWheels ? 0.5f : 1));
	}

	public static void driveForwardByDegrees(float wheelTurn, int speed) {
		int motorAngle = getMotorAngle(wheelTurn, true);
		Movement.leftWheel.startSynchronization();
		Movement.moveLeft(motorAngle, speed / 2, true);
		Movement.moveRight(motorAngle, speed / 2, true);
		Movement.leftWheel.endSynchronization();
	}
	
	public static void driveCurve(boolean turnLeft, float wheelTurn, int speed) {
		int motorAngle = getMotorAngle(wheelTurn, true);
		Movement.leftWheel.startSynchronization();
		Movement.moveLeft(motorAngle / (!turnLeft? 1:2), speed / 2, true);
		Movement.moveRight(motorAngle / (turnLeft? 1:2), speed / 2, true);
		Movement.leftWheel.endSynchronization();
	}
}

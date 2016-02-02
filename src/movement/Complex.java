package movement;

import mindstormer.Constants;

public class Complex {
	
	
	private Complex() {};
	public static void rotate(float angle, float speed) {
		int motorAngle = getMotorAngle(angle, true);
    	Movement.stop();
		Movement.setSpeedLeft(speed);
		Movement.rotateLeft(-motorAngle, true);
		Movement.setSpeedRight(speed);
		Movement.rotateRight(motorAngle, true);
	}
	
	
	public static void rotateLeft(float angle, float speed) {
		int motorAngle = getMotorAngle(angle, false);
    	Movement.stop();
		Movement.setSpeedLeft(speed);
		Movement.rotateLeft(motorAngle, true);
	}
	
	public static void rotateRight(float angle, float speed) {
		int motorAngle = getMotorAngle(angle, false);
    	Movement.stop();
    	Movement.setSpeedRight(speed);
		Movement.rotateRight(motorAngle, true);
	}
	
	private static int getMotorAngle(float angle, boolean bothWheels) {
		return Math.round(Constants.ROTATION_FACTOR * angle * (bothWheels ? 0.5f : 1));
	}
}

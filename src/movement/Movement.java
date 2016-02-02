package movement;

import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;

public final class Movement {
	final static NXTRegulatedMotor leftWheel = Motor.A;
	final static NXTRegulatedMotor rightWheel = Motor.D;
	
	private Movement() {};
		
	public static void setLeft(Mode mode, float speed) {
		setMode(Wheel.LEFT, mode);
		setSpeedLeft(speed);	
	}
	
	public static void setRight(Mode mode, float speed) {
		setMode(Wheel.RIGHT, mode);
		setSpeedRight(speed);
	}
	
	public static void setMode(Mode mode) {
		setModeLeft(mode);
		setModeRight(mode);
	}
	
	public static void setModeLeft(Mode mode) {
		setMode(Wheel.LEFT, mode);
	}
	
	public static void setModeRight(Mode mode) {
		setMode(Wheel.RIGHT, mode);
	}
	
	public static void setSpeed(float speed) {
		setSpeedLeft(speed);
		setSpeedRight(speed);
	}
	
	public static void setSpeedLeft(float speed) {
		leftWheel.setSpeed(speed);
		State.leftSpeed = speed;
	}
	
	public static void setSpeedRight(float speed) {
		rightWheel.setSpeed(speed);
		State.rightSpeed = speed;
	}
	
	public static void set(Mode leftMode, float leftSpeed, Mode rightMode , float rightSpeed) {
		setLeft(leftMode, leftSpeed);
		setRight(rightMode, rightSpeed);
	}
	
	public static void stop() {
		stopLeft();
		stopRight();
	}
	
	public static void stopLeft() {
		leftWheel.stop();
		State.leftMode = Mode.STOP;
	}
	
	public static void stopRight() {
		rightWheel.stop();
		State.rightMode = Mode.STOP;
	}
	
	private static void setMode(Wheel wheel, Mode mode) {
		NXTRegulatedMotor selectedWheel;
		
		if (wheel == Wheel.LEFT) {
			selectedWheel = leftWheel;
			State.leftMode = mode;
		} else {
			selectedWheel = rightWheel;
			State.rightMode = mode;
		}
		
		if(Mode.FORWARD == mode) {
			selectedWheel.forward();
		} else if (Mode.BACKWARD == mode) {
			selectedWheel.backward();
		} else if (Mode.STOP == mode) {
			selectedWheel.stop();
		}
	}
	
	public static enum Mode {
		FORWARD, BACKWARD, STOP;
	}
	
	private static enum Wheel {
		LEFT, RIGHT;
	}
}
package edu.kit.mindstormer.movement;

import edu.kit.mindstormer.Constants;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;

public final class Movement {
	private final static NXTRegulatedMotor leftWheel = Motor.A;
	private final static NXTRegulatedMotor rightWheel = Motor.D;
	private final static MotorListener leftMotorListener = new MotorListener();
	private final static MotorListener rightMotorListener = new MotorListener();
	
	private Movement() {};
	
	public static void init() {
		leftWheel.setAcceleration(Constants.ACCELERATION);
		rightWheel.setAcceleration(Constants.ACCELERATION);
		leftWheel.addListener(leftMotorListener);
		rightWheel.addListener(rightMotorListener);
	}
	
	public static void moveLeft(float speed) {
		setMode(Wheel.LEFT, speed);
		leftWheel.setSpeed(speed);
	}
	
	public static void moveRight(float speed) {
		setMode(Wheel.RIGHT, speed);
		rightWheel.setSpeed(speed);
	}
	
	public static void move(float speed) {
		moveLeft(speed);
		moveRight(speed);
	}
	
	public static void moveLeft(int angle, float speed, boolean immediateReturn) {
		leftWheel.setSpeed(speed);
		leftWheel.rotate(angle, immediateReturn);
	}
	
	public static void moveRight(int angle, float speed, boolean immediateReturn) {
		rightWheel.setSpeed(speed);
		rightWheel.rotate(angle, immediateReturn);
	}
	
	
	public static void move(float leftSpeed, float rightSpeed) {
		moveLeft(leftSpeed);
		moveRight(rightSpeed);
	}
	
	public static void stop() {
		stopLeft();
		stopRight();
	}
	
	public static void stopLeft() {
		leftWheel.stop();
	}
	
	public static void stopRight() {
		rightWheel.stop();
	}
	
	private static float setMode(Wheel wheel, float speed) {
		if (speed > 0) {
			setMode(wheel, Mode.FORWARD);
		} else if (speed < 0) {
			setMode(wheel, Mode.BACKWARD);
		} else {
			setMode(wheel, Mode.STOP);
		}
		return Math.abs(speed);
	}
	
	private static void setMode(Wheel wheel, Mode mode) {
		NXTRegulatedMotor selectedWheel;
		
		if (wheel == Wheel.LEFT) {
			selectedWheel = leftWheel;
		} else {
			selectedWheel = rightWheel;
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
package edu.kit.mindstormer.movement;

import edu.kit.mindstormer.Constants;
import lejos.hardware.BrickFinder;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.robotics.RegulatedMotor;

public final class Movement {
	protected final static RegulatedMotor leftWheel = new EV3LargeRegulatedMotor(BrickFinder.getDefault().getPort("A"));
	protected final static RegulatedMotor rightWheel = new EV3LargeRegulatedMotor(BrickFinder.getDefault().getPort("D"));
	protected final static MotorListener leftMotorListener = new MotorListener();
	protected final static MotorListener rightMotorListener = new MotorListener();

	private Movement() {
	};

	public static void init() {
		leftWheel.setAcceleration(Constants.ACCELERATION);
		rightWheel.setAcceleration(Constants.ACCELERATION);
		leftWheel.addListener(leftMotorListener);
		rightWheel.addListener(rightMotorListener);
		leftWheel.synchronizeWith(new RegulatedMotor[] {rightWheel});
	}

	public static void moveLeft(int speed) {
		setMode(Wheel.LEFT, speed);
		leftWheel.setSpeed(speed);
	}

	public static void moveRight(int speed) {
		setMode(Wheel.RIGHT, speed);
		rightWheel.setSpeed(speed);
	}

	public static void move(int speed) {
		leftWheel.startSynchronization();
		moveLeft(speed);
		moveRight(speed);
		leftWheel.endSynchronization();
	}

	public static void moveLeft(int angle, int speed, boolean immediateReturn) {
		leftWheel.setSpeed(speed);
		leftWheel.rotate(angle, immediateReturn);
	}

	public static void moveRight(int angle, int speed, boolean immediateReturn) {
		rightWheel.setSpeed(speed);
		rightWheel.rotate(angle, immediateReturn);
	}

	public static void move(int leftSpeed, int rightSpeed) {
		leftWheel.startSynchronization();
		moveLeft(leftSpeed);
		moveRight(rightSpeed);
		leftWheel.endSynchronization();
	}

	public static void stop() {
		leftWheel.startSynchronization();
		stopRight();
		stopLeft();
		leftWheel.endSynchronization();
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
		RegulatedMotor selectedWheel = (wheel == Wheel.LEFT) ? leftWheel : rightWheel;

		if (Mode.FORWARD == mode) {
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
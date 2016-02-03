package edu.kit.mindstormer.movement;

import edu.kit.mindstormer.Constants;
import lejos.hardware.BrickFinder;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.robotics.RegulatedMotor;

public final class Movement {
	protected final static RegulatedMotor leftWheel = new EV3LargeRegulatedMotor(
			BrickFinder.getDefault().getPort("A"));
	protected final static RegulatedMotor rightWheel = new EV3LargeRegulatedMotor(
			BrickFinder.getDefault().getPort("D"));
	protected final static RegulatedMotor sensorMotor = new EV3MediumRegulatedMotor(
			BrickFinder.getDefault().getPort("B"));
	protected final static MotorListener leftMotorListener = new MotorListener();
	protected final static MotorListener rightMotorListener = new MotorListener();

	private Movement() {
	};

	public static void init() {
		leftWheel.setAcceleration(Constants.ACCELERATION);
		rightWheel.setAcceleration(Constants.ACCELERATION);
		leftWheel.addListener(leftMotorListener);
		rightWheel.addListener(rightMotorListener);
		leftWheel.synchronizeWith(new RegulatedMotor[] { rightWheel });
	}

	public static void moveLeft(int speed) {
		leftWheel.setSpeed(speed);
		setMode(Wheel.LEFT, speed);
	}

	public static void moveRight(int speed) {
		rightWheel.setSpeed(speed);
		setMode(Wheel.RIGHT, speed);
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

	public static void moveRobot(int speed) {
		move(speed, speed);
	}

	public static void moveDistance(float distance, int speed) {
		float wheelTurn = distance * 720f/118.5f;
		driveForwardByDegrees(wheelTurn, speed);
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
		RegulatedMotor selectedWheel = (wheel == Wheel.LEFT) ? leftWheel
				: rightWheel;

		if (Mode.FORWARD == mode) {
			selectedWheel.forward();
		} else if (Mode.BACKWARD == mode) {
			selectedWheel.backward();
		} else if (Mode.STOP == mode) {
			selectedWheel.stop();
		}
	}

	public static void rotate(float angle, int speed, boolean immediateReturn) {
		int motorAngle = getMotorAngle(angle, true);
		leftWheel.startSynchronization();
		moveLeft(-motorAngle, speed / 2, immediateReturn);
		moveRight(motorAngle, speed / 2, immediateReturn);
		leftWheel.endSynchronization();
	}

	public static void rotateLeft(float angle, int speed) {
		int motorAngle = getMotorAngle(angle, false);
		stop();
		moveLeft(motorAngle, speed, true);
	}

	public static void rotateRight(float angle, int speed) {
		int motorAngle = getMotorAngle(angle, false);
		stop();
		moveRight(motorAngle, speed, true);
	}

	private static int getMotorAngle(float angle, boolean bothWheels) {
		return Math.round(Constants.ROTATION_FACTOR * angle
				* (bothWheels ? 0.5f : 1));
	}

	public static void driveForwardByDegrees(float wheelTurn, int speed) {
		int motorAngle = getMotorAngle(wheelTurn, true);
		leftWheel.startSynchronization();
		moveLeft(motorAngle, speed / 2, true);
		moveRight(motorAngle, speed / 2, true);
		leftWheel.endSynchronization();
	}

	public static void driveCurve(boolean turnLeft, float wheelTurn, int speed) {
		int motorAngle = getMotorAngle(wheelTurn, true);
		leftWheel.startSynchronization();
		moveLeft(motorAngle, speed / (!turnLeft ? 1 : 2), true);
		moveRight(motorAngle, speed / (turnLeft ? 1 : 2), true);
		leftWheel.endSynchronization();
	}

	public static void rotateSensorMotor(int angle) {
		sensorMotor.rotate(
				Math.round(Constants.SENSOR_ROTATION_FACTOR * angle), true);
	}

	public static enum Mode {
		FORWARD, BACKWARD, STOP;
	}

	private static enum Wheel {
		LEFT, RIGHT;
	}
}
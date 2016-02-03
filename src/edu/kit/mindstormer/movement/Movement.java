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

	public static void moveLeft(int angle, int speed) {
		leftWheel.setSpeed(speed);
		leftWheel.rotate(angle, true);
	}

	public static void moveRight(int angle, int speed) {
		rightWheel.setSpeed(speed);
		rightWheel.rotate(angle, true);
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

	public static void rotate(float angle, int speed) {
		int motorAngle = getMotorAngleForRotation(angle, true);
		leftWheel.startSynchronization();
		moveLeft(-motorAngle, speed / 2);
		moveRight(motorAngle, speed / 2);
		leftWheel.endSynchronization();
	}

	public static void rotateLeft(float angle, int speed) {
		int motorAngle = getMotorAngleForRotation(angle, false);
		stop();
		moveLeft(motorAngle, speed);
	}

	public static void rotateRight(float angle, int speed) {
		int motorAngle = getMotorAngleForRotation(angle, false);
		stop();
		moveRight(motorAngle, speed);
	}

	private static int getMotorAngleForRotation(float angle, boolean bothWheels) {
		return Math.round(Constants.ROTATION_FACTOR * angle * (bothWheels ? 0.5f : 1));
	}
	
	private static int getMotorAngleForDistance(float distance, boolean bothWheels) {
		return Math.round(Constants.DISTANCE_FACTOR * distance * (bothWheels ? 0.5f : 1));
	}

	public static void moveDistance(float distance, int speed) {
		int motorAngle = getMotorAngleForDistance(distance, true);
		leftWheel.startSynchronization();
		moveLeft(motorAngle, speed / 2);
		moveRight(motorAngle, speed / 2);
		leftWheel.endSynchronization();
	}

	public static void driveCurve(boolean turnLeft, float distance, int speed, float curveStrength) {
		int motorAngle = getMotorAngleForDistance(distance, true);
		leftWheel.startSynchronization();
		moveLeft(motorAngle, (int)(speed / (!turnLeft? 1:curveStrength)));
		moveRight(motorAngle, (int)(speed / (turnLeft? 1:curveStrength)));
		leftWheel.endSynchronization();
	}

	public static void rotateSensorMotor(int angle) {
		sensorMotor.rotate(Math.round(Constants.SENSOR_ROTATION_FACTOR * angle), true);
	}

	public static enum Mode {
		FORWARD, BACKWARD, STOP;
	}

	private static enum Wheel {
		LEFT, RIGHT;
	}
}
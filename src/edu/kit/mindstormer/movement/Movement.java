package edu.kit.mindstormer.movement;

import edu.kit.mindstormer.Constants;
import edu.kit.mindstormer.program.OperatingSystem;
import edu.kit.mindstormer.sensor.Sensor;
import lejos.hardware.BrickFinder;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public final class Movement {
	protected final static RegulatedMotor leftMotor = new EV3LargeRegulatedMotor(
			BrickFinder.getDefault().getPort("D"));
	protected final static RegulatedMotor rightMotor = new EV3LargeRegulatedMotor(
			BrickFinder.getDefault().getPort("A"));
	protected final static RegulatedMotor sensorMotor = new EV3MediumRegulatedMotor(
			BrickFinder.getDefault().getPort("B"));
	protected final static MotorListener motorListener = new MotorListener();

	private Movement() {
	};

	public static void init() {
		leftMotor.setAcceleration(Constants.ACCELERATION);
		rightMotor.setAcceleration(Constants.ACCELERATION);
		leftMotor.addListener(motorListener);
		rightMotor.addListener(motorListener);
		sensorMotor.addListener(motorListener);
		leftMotor.synchronizeWith(new RegulatedMotor[] { rightMotor });
		sensorMotor.setSpeed(Constants.SENSOR_MOTOR_SPEED);
	}

	public static void moveLeft(float centimeterPerSecond) {
		leftMotor.setSpeed(cmPerSecondToSpeed(centimeterPerSecond));
		setMode(Wheel.LEFT, centimeterPerSecond);
	}

	public static void moveRight(float centimeterPerSecond) {
		rightMotor.setSpeed(cmPerSecondToSpeed(centimeterPerSecond));
		setMode(Wheel.RIGHT, centimeterPerSecond);
	}

	public static void move(float centimeterPerSecond) {
		leftMotor.startSynchronization();
		moveLeft(centimeterPerSecond);
		moveRight(centimeterPerSecond);
		leftMotor.endSynchronization();
	}

	public static void moveLeft(int angle, float centimeterPerSecond) {
		leftMotor.setSpeed(cmPerSecondToSpeed(centimeterPerSecond));
		leftMotor.rotate(angle, true);
	}

	public static void moveRight(int angle, float centimeterPerSecond) {
		rightMotor.setSpeed(cmPerSecondToSpeed(centimeterPerSecond));
		rightMotor.rotate(angle, true);
	}

	public static void move(float leftCentimeterPerSecond, float rightCentimeterPerSecond) {
		leftMotor.startSynchronization();
		moveLeft(leftCentimeterPerSecond);
		moveRight(rightCentimeterPerSecond);
		leftMotor.endSynchronization();
	}
	
	public static void moveDistance(float distance, float centimeterPerSecond) {
		int backwardsFactor = (distance < 0 || centimeterPerSecond < 0) ? -1 : 1;
		int motorAngle = getMotorAngleForDistance(distance);
		leftMotor.startSynchronization();
		moveLeft(backwardsFactor * motorAngle, centimeterPerSecond);
		moveRight(backwardsFactor * motorAngle, centimeterPerSecond);
		leftMotor.endSynchronization();
	}

	public static void stop() {
		leftMotor.startSynchronization();
		stopRight();
		stopLeft();
		leftMotor.endSynchronization();
	}

	public static void stopLeft() {
		leftMotor.stop();
	}

	public static void stopRight() {
		rightMotor.stop();
	}

	private static float setMode(Wheel wheel, float leftCentimeterPerSecond) {
		if (leftCentimeterPerSecond > 0) {
			setMode(wheel, Mode.FORWARD);
		} else if (leftCentimeterPerSecond < 0) {
			setMode(wheel, Mode.BACKWARD);
		} else {
			setMode(wheel, Mode.STOP);
		}
		return Math.abs(leftCentimeterPerSecond);
	}

	private static void setMode(Wheel wheel, Mode mode) {
		RegulatedMotor selectedWheel = (wheel == Wheel.LEFT) ? leftMotor
				: rightMotor;

		if (Mode.FORWARD == mode) {
			selectedWheel.forward();
		} else if (Mode.BACKWARD == mode) {
			selectedWheel.backward();
		} else if (Mode.STOP == mode) {
			selectedWheel.stop();
		}
	}

	public static void rotate(float angle, float centimeterPerSecond) {
		int motorAngle = getMotorAngleForRotation(angle, true);
		leftMotor.startSynchronization();
		moveLeft(motorAngle, cmPerSecondToSpeed(centimeterPerSecond) / 2);
		moveRight(-motorAngle, cmPerSecondToSpeed(centimeterPerSecond) / 2);
		leftMotor.endSynchronization();
	}

	public static void rotateLeft(float angle, float centimeterPerSecond) {
		int motorAngle = getMotorAngleForRotation(angle, false);
		moveLeft(motorAngle, centimeterPerSecond);
	}

	public static void rotateRight(float angle, float centimeterPerSecond) {
		int motorAngle = getMotorAngleForRotation(angle, false);
		moveRight(motorAngle, centimeterPerSecond);
	}

	private static int getMotorAngleForRotation(float angle, boolean bothWheels) {
		return Math.round(Constants.VEHICLE_ANGLE_TO_MOTOR_ANGLE * angle * (bothWheels ? 0.5f : 1));
	}
	
	private static int getMotorAngleForDistance(float distance) {
		return Math.round(Constants.CM_TO_MOTOR_ANGLE * distance);
	}
	
	private static int cmPerSecondToSpeed(float centimeterPerSecond) {
		return Math.round(Constants.CM_TO_MOTOR_ANGLE * centimeterPerSecond);
	}
	
	public static void rotateSensorMotor(int angle) {
		sensorMotor.rotate(-Math.round(Constants.SENSOR_ANGLE_TO_MOTOR_ANGLE * angle), true);
	}
	

	
	public static void holdDistance(float centimeterPerSecond, float distance) {
		float turningFactor = 7f / 8f;
		int delayTime = 1000;
		float centimeterTraveled = centimeterPerSecond * delayTime / 1000.f;
		
		// auf wand zufahren = minus angle

		move(centimeterPerSecond);
		float sample1 = Sensor.sampleDistance();
		Delay.msDelay(delayTime);
		float sample2 = Sensor.sampleDistance();
		stop();
		float difference = sample2 - sample1;
		Delay.msDelay(30);
		
		float angleToWall = (float) Math.toDegrees(Math.atan(difference / centimeterTraveled));
		OperatingSystem.displayText(angleToWall + "");
		
		float proportionalDistance = Math.abs(difference) / distance;
		float proportionalAngle = 45 * proportionalDistance;
		float angleCorrection = proportionalAngle - angleToWall;
		
		if (sample2 > distance) {
			rotate(-angleCorrection, 10);
			State.waitForMotors(true, true);
			//angle should be negative
		} else if (sample2 < distance) {
			rotate(angleCorrection, 10);
			State.waitForMotors(true, true);
			//angle should be positive
		} else {
		}
		Delay.msDelay(30);
		
		/*
		if (difference > 0 && sample > distance) {
			move(centimeterPerSecond, turningFactor * centimeterPerSecond);
		} else if (difference < 0 && sample < distance){
			move(turningFactor * centimeterPerSecond, centimeterPerSecond);
		} else {
			move(centimeterPerSecond);
		}*/
		
	}
	
	public static void moveParallel(float centimeterPerSecond, float distance) {
		float sampleDistance = Constants.DEFAULT_SAMPLE_DISTANCE;
		alignParallel(centimeterPerSecond, sampleDistance);
		float sample = Sensor.sampleDistance();
		float alignmentError = distance - sample;
		if (Math.abs(alignmentError) > Constants.MAX_ALIGNMENT_ERROR) {
			rotate(90, 14);
			State.waitForMotors(true, true);
			moveDistance(alignmentError, -centimeterPerSecond);
			State.waitForMotors(true, true);
			rotate(-90, 14);
			State.waitForMotors(true, true);
		}
	}
	
	public static void alignParallel(float centimeterPerSecond) {
		alignParallel(centimeterPerSecond, Constants.DEFAULT_SAMPLE_DISTANCE);
	}
	
	
	public static void alignParallel(float centimeterPerSecond, float sampleDistance) {
		float sample = Sensor.sampleDistance();
		if (sample > 30f) {
			return;
		}
		moveDistance(sampleDistance, centimeterPerSecond);
		while (!State.stopped(true, true)) {}
		stop();
		float sampleDifference = (sample - Sensor.sampleDistance());
		Delay.msDelay(10);
		rotate((centimeterPerSecond > 0 ? -1.f : 1.f) * (float) Math.toDegrees(Math.atan(sampleDifference / sampleDistance)), 14);
		while (!State.stopped(true, true)) {}
	}

	public static enum Mode {
		FORWARD, BACKWARD, STOP;
	}

	private static enum Wheel {
		LEFT, RIGHT;
	}
}

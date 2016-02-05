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
	

	
    //================================================================================
    // basic operations
    //================================================================================

	
	public static void moveLeft(boolean forward, float centimeterPerSecond) {
		leftMotor.setSpeed(cmPerSecondToSpeed(centimeterPerSecond, false));
		setMode(leftMotor, forward);
	}

	public static void moveRight(boolean forward, float centimeterPerSecond) {
		rightMotor.setSpeed(cmPerSecondToSpeed(centimeterPerSecond, false));
		setMode(rightMotor, forward);
	}

	private static void moveLeft(int angle, float centimeterPerSecond) {
		leftMotor.setSpeed(cmPerSecondToSpeed(centimeterPerSecond, false));
		leftMotor.rotate(angle, true);
	}

	private static void moveRight(int angle, float centimeterPerSecond) {
		rightMotor.setSpeed(cmPerSecondToSpeed(centimeterPerSecond, false));
		rightMotor.rotate(angle, true);
	}
	
	public static void stopLeft() {
		leftMotor.stop();
	}

	public static void stopRight() {
		rightMotor.stop();
	}
	
	
	
	//================================================================================
    // synchronized operations
    //================================================================================

	public static void move(boolean leftForward, float leftCentimeterPerSecond, boolean rightForward, float rightCentimeterPerSecond) {
		leftMotor.startSynchronization();
		moveLeft(leftForward, leftCentimeterPerSecond);
		moveRight(rightForward, rightCentimeterPerSecond);
		leftMotor.endSynchronization();
	}
	
	public static void move(boolean forward, float centimeterPerSecond) {
		move(forward, centimeterPerSecond, forward, centimeterPerSecond);
	}
	
	public static void moveDistance(float distance, float centimeterPerSecond) {
		int motorAngle = getMotorAngleForDistance(distance);
		leftMotor.startSynchronization();
		moveLeft(motorAngle, centimeterPerSecond);
		moveRight(motorAngle, centimeterPerSecond);
		leftMotor.endSynchronization();
	}

	public static void stop() {
		leftMotor.startSynchronization();
		stopRight();
		stopLeft();
		leftMotor.endSynchronization();
	}

	
	
	//================================================================================
    // rotation operations
    //================================================================================

	public static void rotate(float angle, float centimeterPerSecond) {
		int motorAngle = getMotorAngleForRotation(angle, true);
		leftMotor.startSynchronization();
		moveLeft(motorAngle, centimeterPerSecond / 2);
		moveRight(-motorAngle, centimeterPerSecond / 2);
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
	
	public static void rotateSensorMotor(int angle) {
		sensorMotor.rotate(-Math.round(Constants.SENSOR_ANGLE_TO_MOTOR_ANGLE * angle), true);
	}
	
	
	
	//================================================================================
    // alignment operations
    //================================================================================

	public static void holdDistance(boolean forward, float centimeterPerSecond, float distance) {
		float turningFactor = 7f / 8f;
		int delayTime = 1000;
		float centimeterTraveled = centimeterPerSecond * delayTime / 1000.f;
		
		// auf wand zufahren = minus angle

		move(forward, centimeterPerSecond);
		float sample1 = Sensor.sampleDistance();
		Delay.msDelay(delayTime);
		float sample2 = Sensor.sampleDistance();
		float difference = sample2 - sample1;
		stop();
		
		float angleToWall = (float) Math.toDegrees(Math.atan(difference / centimeterTraveled));
		OperatingSystem.displayText(angleToWall + " " + sample2);
		
		float proportionalDistance = Math.abs(distance - sample2) / distance;
		float proportionalAngle = 20 * proportionalDistance;
		float angleCorrection = proportionalAngle - angleToWall;
		
		if (sample2 > distance) {
			rotate(angleCorrection, 10);
			//angle should be negative
		} else if (sample2 < distance) {
			rotate(-angleCorrection, 10);
			//angle should be positive
		} else {
		}
		
		State.waitForMotors(true, true);
		stop();
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
	
	public static void holdDistance2(boolean forward, float centimeterPerSecond, float distance) {
	    float sample = Sensor.sampleDistance();
		Delay.msDelay(200);
		float difference = Sensor.sampleDistance() - sample;

		if (difference > 0 && sample > distance) {
			move(forward ,centimeterPerSecond,forward , (7f / 8f) * centimeterPerSecond);
		} else if (difference < 0 && sample < distance){
			move(forward ,(7f / 8f) * centimeterPerSecond,forward , centimeterPerSecond);
		} else {
			move(forward ,centimeterPerSecond);
		}
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

	
	
	//================================================================================
    // helpers
    //================================================================================
	
	private static int getMotorAngleForRotation(float angle, boolean bothWheels) {
		return Math.round(Constants.VEHICLE_ANGLE_TO_MOTOR_ANGLE * angle * (bothWheels ? 0.5f : 1));
	}
	
	private static int getMotorAngleForDistance(float distance) {
		return Math.round(Constants.CM_TO_MOTOR_ANGLE * distance);
	}
	
	private static int cmPerSecondToSpeed(float centimeterPerSecond, boolean bothWheels) {
		return Math.abs(Math.round(Constants.CM_TO_MOTOR_ANGLE * centimeterPerSecond * (bothWheels ? 0.5f : 1)));
	}
	
	private static void setMode(RegulatedMotor motor, boolean forward) {
		if (forward) {
			motor.forward();
		} else {
			motor.backward();
		}
	}
}

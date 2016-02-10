package edu.kit.mindstormer;

import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;

public final class Constants {

	public final static Port COLOR_SENSOR_PORT = SensorPort.S2;
	public final static Port DISTANCE_SENSOR_PORT = SensorPort.S3;
	public final static Port TOUCH_SENSOR_PORT_RIGHT = SensorPort.S1;
	public final static Port TOUCH_SENSOR_PORT_LEFT = SensorPort.S4;

	/**
	 * Translates distance into motor rotation.
	 */
	public final static float CM_TO_MOTOR_ANGLE = 14.7f;

	/**
	 * Translates vehicle rotation into motor rotation.
	 */
	public final static float VEHICLE_ANGLE_TO_MOTOR_ANGLE = 4.55f;

	/**
	 * Translates sensor rotation into motor rotation.
	 */
	public final static float SENSOR_ANGLE_TO_MOTOR_ANGLE = 26f;

	public final static float DEFAULT_SAMPLE_DISTANCE = 10f;
	public final static float MAX_ALIGNMENT_ERROR = 1f;

	public final static int SENSOR_MOTOR_SPEED = 800;

	public final static float LINE_COLOR_THRESHOLD = 0.15f;
	public final static float LINE_COLOR_THRESHOLD_LAB = 0.15f;
	
	
	public static final float WOOD_COLOR_THRESHOLD = 0.35f;

	public final static int ACCELERATION = 1000;

//	public final static float TOUCH_SENSOR_PRESSED = 1.0f;
//
//	public final static float TOUCH_SENSOR_UNPRESSED = 0.0f;
//
//	public final static float BOTH_TOUCH_SENSOR_PRESSED = 2.0f;
	
	public final static float WHEEL_DISTANCE = 17.5f;
	
	public final static float MIN_WALL_DISTANCE = 4.0f;
	
	public final static float MAX_WALL_DISTANCE = 40.0f;
	
	public final static float WALL_DISTANCE_RACE = 70.0f;
	

	private Constants() {
	}
}

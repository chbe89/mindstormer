package edu.kit.mindstormer;

import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;

public final class Constants {
	
	
	public final static Port COLOR_SENSOR_PORT = SensorPort.S2;
	public final static Port DISTANCE_SENSOR_PORT = SensorPort.S3;
	
	/**
	 * Translates wheel rotation into motor rotation.
	 */
	public final static float ROTATION_FACTOR = 4.55f;
	
	/**
	 * Translates sensor rotation into motor rotation.
	 */
	public final static float SENSOR_ROTATION_FACTOR = 25.7f;
	
	/**
	 * Translates distance into motor rotation.
	 */
	public final static float DISTANCE_FACTOR = 27.64f;
	
	public final static float LINE_COLOR_THRESHOLD = 0.08f;
	
	public final static int ACCELERATION = 1000;
	
	
	
	
	private Constants() {};
}

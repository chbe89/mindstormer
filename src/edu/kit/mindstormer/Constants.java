package edu.kit.mindstormer;

import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;

public final class Constants {
	
	
	public final static Port COLOR_SENSOR_PORT = SensorPort.S2;
	
	/**
	 * Translates wheel rotation into motor rotation.
	 */
	public final static float ROTATION_FACTOR = 4.55f;
	public final static float LINE_COLOR_THRESHOLD = 0.08f;
	
	public final static int ACCELERATION = 2000;
	
	
	private Constants() {};
}

package edu.kit.mindstormer.sensor;

import lejos.hardware.sensor.BaseSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import edu.kit.mindstormer.Constants;

public class Sensor {
	private static BaseSensor COLOR;
	private static BaseSensor DISTANCE = new EV3UltrasonicSensor(Constants.DISTANCE_SENSOR_PORT);
	private static BaseSensor TOUCH_RIGHT = new EV3TouchSensor(Constants.TOUCH_SENSOR_PORT);
	private static BaseSensor TOUCH_LEFT = new EV3TouchSensor(Constants.TOUCH_SENSOR_PORT_2);
	
	private static float[] colorSample;
	private static float[] touchSampleRight;
	private static float[] touchSampleLeft;
	private static float[] distanceSample;

	
	public static void init() {
		colorSample = new float[COLOR.sampleSize()];
		touchSampleRight = new float[TOUCH_RIGHT.sampleSize()];
		touchSampleLeft = new float[TOUCH_LEFT.sampleSize()];
		distanceSample = new float[DISTANCE.sampleSize()];
		
		COLOR.setCurrentMode("Red");
		DISTANCE.setCurrentMode("Distance");
	}
	
	public static void initSensors() {
		for (int i = 0; i < 4; i++) {
			boolean success = false;
			for (int j = 0; j < 5 && !success; j++) {
				switch(i) {
					case 0: success = initColor(); break;
					case 1: success = initDistance();  break;
					case 2: success = initTouchRight();  break;
					case 3: success = initTouchLeft();  break;
				}
			}
		}
	}
	
	public static boolean initColor() {
		try {
			COLOR = new EV3ColorSensor(Constants.COLOR_SENSOR_PORT);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public static boolean initDistance() {
		try {
			DISTANCE = new EV3UltrasonicSensor(Constants.DISTANCE_SENSOR_PORT);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public static boolean initTouchRight() {
		try {
			TOUCH_RIGHT = new EV3TouchSensor(Constants.TOUCH_SENSOR_PORT);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public static boolean initTouchLeft() {
		try {
			TOUCH_LEFT = new EV3TouchSensor(Constants.TOUCH_SENSOR_PORT_2);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static float sampleColor() {
		COLOR.fetchSample(colorSample, 0);
		return colorSample[0];
	}
	
	public static float sampleTouchRight(){
		TOUCH_RIGHT.fetchSample(touchSampleRight, 0);
		return touchSampleRight[0];
	}
	
	public static float sampleTouchLeft(){
		TOUCH_LEFT.fetchSample(touchSampleLeft, 0);
		return touchSampleLeft[0];
	}
	
	public static float sampleTouchBoth(){
		return sampleTouchLeft() + sampleTouchRight();
	}
	
	public static float sampleDistance() {
		DISTANCE.fetchSample(distanceSample, 0);
		return 100 * distanceSample[0];
	}
}

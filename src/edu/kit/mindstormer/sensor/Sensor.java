package edu.kit.mindstormer.sensor;

import lejos.hardware.sensor.BaseSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import edu.kit.mindstormer.Constants;

public class Sensor {
	private static BaseSensor COLOR;
	private static BaseSensor DISTANCE;
	private static BaseSensor TOUCH_LEFT;
	private static BaseSensor TOUCH_RIGHT;
	
	private static float[] colorSample;
	private static float[] touchSampleLeft;
	private static float[] touchSampleRight;
	private static float[] distanceSample;

	
	public static void init() {
		initSensors();
		
		colorSample = new float[COLOR.sampleSize()];
		distanceSample = new float[DISTANCE.sampleSize()];
		touchSampleLeft = new float[TOUCH_LEFT.sampleSize()];
		touchSampleRight = new float[TOUCH_RIGHT.sampleSize()];
		
		COLOR.setCurrentMode("Red");
		DISTANCE.setCurrentMode("Distance");
	}
	
	public static void initSensors() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 5; j++) {
				try {
					switch(i) {
						case 0: COLOR = new EV3ColorSensor(Constants.COLOR_SENSOR_PORT); break;
						case 1: DISTANCE = new EV3UltrasonicSensor(Constants.DISTANCE_SENSOR_PORT); break;
						case 2: TOUCH_LEFT = new EV3TouchSensor(Constants.TOUCH_SENSOR_PORT_LEFT); break;
						case 3: TOUCH_RIGHT = new EV3TouchSensor(Constants.TOUCH_SENSOR_PORT_RIGHT); break;
					}
				} catch (Exception e) {
					 continue;
				}
				break;
			}
		}
	}


	public static float sampleColor() {
		COLOR.fetchSample(colorSample, 0);
		return colorSample[0];
	}
	
	public static boolean sampleTouchLeft(){
		TOUCH_LEFT.fetchSample(touchSampleLeft, 0);
		return touchSampleLeft[0] > 0;
	}
	
	public static boolean sampleTouchRight(){
		TOUCH_RIGHT.fetchSample(touchSampleRight, 0);
		return touchSampleRight[0] > 0;
	}
	
	public static boolean sampleTouchBoth(){
		return sampleTouchLeft() || sampleTouchRight();
	}
	
	public static float sampleDistance() {
		DISTANCE.fetchSample(distanceSample, 0);
		return 100 * distanceSample[0];
	}
}

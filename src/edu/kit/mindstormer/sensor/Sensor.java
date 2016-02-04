package edu.kit.mindstormer.sensor;

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MedianFilter;
import edu.kit.mindstormer.Constants;

;

public class Sensor {
	private static final EV3ColorSensor COLOR = new EV3ColorSensor(Constants.COLOR_SENSOR_PORT);
	private static final EV3UltrasonicSensor DISTANCE = new EV3UltrasonicSensor(Constants.DISTANCE_SENSOR_PORT);
	private static final EV3TouchSensor TOUCH = new EV3TouchSensor(Constants.TOUCH_SENSOR_PORT);
	private static final EV3TouchSensor TOUCH_2 = new EV3TouchSensor(Constants.TOUCH_SENSOR_PORT_2);
	private static final SampleProvider DISTANCE_MEDIAN = new MedianFilter(DISTANCE, 5);

	private static final float[] colorSample = new float[COLOR.sampleSize()];
	private static final float[] touchSample = new float[TOUCH.sampleSize()];
	private static final float[] distanceSample = new float[DISTANCE.sampleSize()];
	private static final float[] distanceMedianSample = new float[DISTANCE_MEDIAN.sampleSize()];

	public static void init() {
		COLOR.setCurrentMode("Red");
		DISTANCE.setCurrentMode("Distance");
	}

	public static float sampleColor() {
		COLOR.fetchSample(colorSample, 0);
		return colorSample[0];
	}
	
	public static float sampleTouchRight(){
		TOUCH.fetchSample(touchSample, 0);
		return touchSample[0];
	}
	
	public static float sampleTouchLeft(){
		TOUCH_2.fetchSample(touchSample, 0);
		return touchSample[0];
	}
	
	public static float sampleTouchBoth(){
		return sampleTouchLeft() + sampleTouchRight();
	}
	
	public static float sampleDistance() {
		DISTANCE.fetchSample(distanceSample, 0);
		return 100 * distanceSample[0];
	}

	public static float sampleDistanceMedian() {
		DISTANCE_MEDIAN.fetchSample(distanceMedianSample, 0);
		return 100 * distanceMedianSample[0];
	}
}

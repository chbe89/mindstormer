package edu.kit.mindstormer.sensor;

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MedianFilter;
import edu.kit.mindstormer.Constants;

;

public class Sensor {
	private static final EV3ColorSensor COLOR = new EV3ColorSensor(Constants.COLOR_SENSOR_PORT);
	private static final EV3IRSensor DISTANCE = new EV3IRSensor(Constants.DISTANCE_SENSOR_PORT);
	private static final SampleProvider DISTANCE_MEDIAN = new MedianFilter(DISTANCE, 5);

	private static final float[] colorSample = new float[COLOR.sampleSize()];
	private static final float[] distanceSample = new float[DISTANCE.sampleSize()];

	public static void init() {
		COLOR.setCurrentMode("Red");
		DISTANCE.setCurrentMode("Distance");
	}

	public static float sampleColor() {
		COLOR.fetchSample(colorSample, 0);
		return colorSample[0];
	}

	public static float sampleDistance() {
		DISTANCE_MEDIAN.fetchSample(distanceSample, 0);
		return distanceSample[0];
	}

	public static boolean hasInfiniteDistance() {
		float distance = sampleDistance();
		return distance == Float.POSITIVE_INFINITY || distance == Float.NEGATIVE_INFINITY;
	}
}

package edu.kit.mindstormer;

import java.util.ArrayList;
import java.util.List;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.BaseSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class Sensors {

	private static final List<BaseSensor> sensors = new ArrayList<>();

	@SuppressWarnings("resource")
	public static SampleProvider getSensor(String type, String portName, String mode) {
		Port port = LocalEV3.get().getPort(portName);
		BaseSensor sensor = null;
		SampleProvider sampleProvider = null;
		switch (type) {
		case "gyro":
			sensor = new EV3GyroSensor(port);
			EV3GyroSensor gyro = (EV3GyroSensor)sensor;
			switch (mode) {
			case "angle":
				sampleProvider = gyro.getAngleMode();
				break;
			case "rate":
				sampleProvider = gyro.getRateMode();
				break;
			case "angleRate":
				sampleProvider = gyro.getAngleAndRateMode();
				break;
			default:
				throw new IllegalArgumentException("sensor MODE not supported: " + mode);
			}
			break;
		case "ultra":
			sensor = new EV3UltrasonicSensor(port);
			break;
		default:
			throw new IllegalArgumentException("sensor type not supported: " + type);
		}

		sensors.add(sensor);
		return sampleProvider;
	}

	public static void closeSensors() {
		for (BaseSensor s : sensors)
			s.close();
	}

}
package movement;

import java.util.ArrayList;
import java.util.List;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.BaseSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;

public class Sensors {

	private static final List<BaseSensor> sensors = new ArrayList<>();

	public static SensorMode getSensor(String type, String portName, String mode) {
		Port port = LocalEV3.get().getPort(portName);
		BaseSensor sensor = null;

		switch (type) {
		case "gyro":
			sensor = new EV3GyroSensor(port);
			break;
		case "ultra":
			sensor = new EV3UltrasonicSensor(port);
			break;
		default:
			throw new IllegalArgumentException("sensor type not supported: " + type);
		}

		sensors.add(sensor);

		SensorMode sensorMode = sensor.getMode(mode);
		return sensorMode;
	}

	public static void closeSensors() {
		for (BaseSensor s : sensors)
			s.close();
	}

}

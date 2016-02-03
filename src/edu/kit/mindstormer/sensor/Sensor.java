package edu.kit.mindstormer.sensor;

import edu.kit.mindstormer.Constants;
import lejos.hardware.sensor.EV3ColorSensor;;

public class Sensor {
	public static final EV3ColorSensor COLOR = new EV3ColorSensor(Constants.COLOR_SENSOR_PORT);
	
	{
		COLOR.setCurrentMode("Red");
	}
	
}

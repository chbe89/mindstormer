package edu.kit.mindstormer.sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;;

public class SensorColor extends EV3ColorSensor {

	
	
	public SensorColor(Port port) {
		super(port);
		
	}
	
	public boolean colorChanged() {
		return true;
	}

}

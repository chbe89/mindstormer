package edu.kit.mindstormer.program.implementation.test;

import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.program.OperatingSystem;
import edu.kit.mindstormer.sensor.Sensor;

public class SensorMotorTest extends AbstractProgram  {
	public SensorMotorTest() {
		super("SensorMotorTest");
	}
	
	public void run() {
		boolean once = true;
		while (!quit.get()) {
			if (once) {
				//Movement.rotateSensorMotor(-90);
				once = false;
				
			}
			OperatingSystem.displayText(Sensor.sampleDistance() + "");
			
		}
	}
}

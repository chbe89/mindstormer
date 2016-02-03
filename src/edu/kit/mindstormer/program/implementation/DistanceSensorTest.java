package edu.kit.mindstormer.program.implementation;

import lejos.utility.Delay;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.program.OperatingSystem;
import edu.kit.mindstormer.sensor.Sensor;

public class DistanceSensorTest extends AbstractProgram {

	@Override
	public void run() {
		while (!quit.get()) {
			float distance = Sensor.sampleDistance();
			OperatingSystem.displayText(String.valueOf(distance));
			Delay.msDelay(200);
		}

	}
}

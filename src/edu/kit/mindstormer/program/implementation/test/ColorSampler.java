package edu.kit.mindstormer.program.implementation.test;

import lejos.utility.Delay;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.program.OperatingSystem;
import edu.kit.mindstormer.sensor.Sensor;
import edu.kit.mindstormer.sensor.Sensor.ColorMode;

public class ColorSampler extends AbstractProgram {

	@Override
	public void run() {
		Sensor.setColorMode(ColorMode.AMBIENT);
		while (!quit.get()) {
			float colorSample = Sensor.sampleColor();
			OperatingSystem.displayText("COLOR: " + colorSample);
			Delay.msDelay(200);
		}
		Sensor.setColorMode(ColorMode.RED);
	}

}

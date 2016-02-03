package edu.kit.mindstormer.program.implementation.test;

import lejos.utility.Delay;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.program.OperatingSystem;
import edu.kit.mindstormer.sensor.Sensor;

public class BlockingTest extends AbstractProgram {

	@Override
	public void run() {
		while (!quit.get()) {
			boolean infiniteDistance = Sensor.hasInfiniteDistance();
			OperatingSystem.displayText("InfiniteDistance: " + infiniteDistance);
			Delay.msDelay(100);
		}
	}
}

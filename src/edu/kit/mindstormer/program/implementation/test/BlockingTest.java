package edu.kit.mindstormer.program.implementation.test;

import lejos.utility.Delay;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.program.OperatingSystem;

public class BlockingTest extends AbstractProgram {

	@Override
	public void run() {
		while (!quit.get()) {
			boolean infiniteDistance = true;
			OperatingSystem.displayText("InfiniteDistance: " + infiniteDistance);
			Delay.msDelay(100);
		}
	}
}

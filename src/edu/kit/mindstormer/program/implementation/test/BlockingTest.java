package edu.kit.mindstormer.program.implementation.test;

import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.program.AbstractProgram;

public class BlockingTest extends AbstractProgram {

	@Override
	public void run() {
		while (!quit.get()) {
			//boolean infiniteDistance = true;
			//OperatingSystem.displayText("InfiniteDistance: " + infiniteDistance);
			//Delay.msDelay(100);
			Movement.alignParallelToWall(250);
		}
	}
}

package edu.kit.mindstormer.program.implementation.test;

import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.program.AbstractProgram;

public class BlockingTest extends AbstractProgram {

	@Override
	public void run() {
		//Movement.moveParallel(15, 20);
		Movement.moveDistance(100, -100);
		while (!quit.get()) {
			//Movement.moveParallel(15, 15);
			//boolean infiniteDistance = true;
			//OperatingSystem.displayText("InfiniteDistance: " + infiniteDistance);
			//Delay.msDelay(100);
			//Movement.holdDistance(20, 15);
		}
		
	}
}

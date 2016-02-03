package edu.kit.mindstormer.program.implementation;

import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.program.AbstractProgram;

public class SensorMotorTest extends AbstractProgram  {
	public SensorMotorTest() {
		super("SensorMotorTest");
	}
	
	public void run() {
		boolean once = true;
		while (!quit.get()) {
			if (once) {
				Movement.rotateSensorMotor(-90);
				once = false;
			}
			
		}
	}
}

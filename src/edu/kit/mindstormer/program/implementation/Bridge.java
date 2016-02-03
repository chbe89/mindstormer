package edu.kit.mindstormer.program.implementation;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.program.AbstractProgram;

public class Bridge extends AbstractProgram {
	float sample;

	public Bridge() {
		super("Bridge");
	}
	
	public void run() {
		sample = 0;
		Movement.rotateSensorMotor(90);
		while (!quit.get()) {
			//Movement.driveCurve(turnLeft, wheelTurn, speed);
			while (sample < 5) {
				
			}
		}
		Movement.rotateSensorMotor(-90);
	}
}

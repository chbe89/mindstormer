package edu.kit.mindstormer.program.implementation;

import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.sensor.Sensor;

public class AfterChain extends AbstractProgram {

	@Override
	public void run() {
		Movement.move(true, 30);
		while(!Sensor.sampleTouchBoth());
		Movement.moveCircle(-90, true, 7, 10);
		State.waitForMovementMotors();
		Movement.moveDistance(40, 10);
		State.waitForMovementMotors();
		Movement.stop();
		
		new RollerBox().startProgram();
		new Race().startProgram();
		new Endboss().startProgram();
	}
}

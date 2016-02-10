package edu.kit.mindstormer.program.implementation;

import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.sensor.Sensor;
import lejos.hardware.Sound;

public class ChainBridge extends AbstractProgram {

	@Override
	public void run() {
		Movement.move(true, 20);
		while(!Sensor.sampleTouchBoth());

		Movement.moveCircle(-90, true, 7, 10);
		State.waitForMovementMotors();
		
		Movement.moveDistance(-40, 10);
		State.waitForMovementMotors();
		
		Sound.beep();
		Movement.stop();
		while(Sensor.sampleDistance() < 40) {
			Movement.holdDistance2(false, 10, 10);
		}
		Sound.beep();
		
		//Movement.move(true, 10);
		//while(Sensor.sampleDistance() > 20);
		//Movement.alignParallel(40, 10, 30);
		//Movement.moveDistance(-40, 10);
		//State.waitForMovementMotors();
		Movement.moveDistance(-170, 45);
		State.waitForMovementMotors();
		
		Movement.move(false, 10);
		while(Sensor.sampleDistance() > 25);
		while(Sensor.sampleDistance() < 15) {
			Movement.holdDistance2(false, 10, 10);
		}
		Movement.moveDistance(20, 10);
		State.waitForMovementMotors();
		Movement.rotate(180, 10);
		State.waitForMovementMotors();
		Movement.move(true, 30);
		while(!Sensor.sampleTouchBoth());
		Movement.moveCircle(-90, true, 7, 10);
		State.waitForMovementMotors();
		Movement.moveDistance(50, 10);
		State.waitForMovementMotors();
		Movement.stop();
		
	}

}

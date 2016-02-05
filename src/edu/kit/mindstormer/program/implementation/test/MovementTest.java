package edu.kit.mindstormer.program.implementation.test;

import java.util.logging.Logger;

import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import lejos.utility.Delay;

public class MovementTest extends AbstractProgram {
	java.util.logging.Logger logger = Logger.getLogger("MovementTest");

	public MovementTest() {
		super("MovementTest");
	}
	
	public void run() {
		combinedMovementTest1();
		Delay.msDelay(2000);
		combinedMovementTest2();
		Delay.msDelay(2000);
		//rotateSensorMotor(90);
		Delay.msDelay(2000);
		moveDistanceTest(20, 10);
		Delay.msDelay(2000);
		rotateTest(90, 10); 
		Delay.msDelay(2000);
		
		
		while (!quit.get()) {
			
		}
	}
	
	private void rotateSensorMotor(int angle) {
		Movement.rotateSensorMotor(angle);
		State.waitForSensorMotor();
		Movement.rotateSensorMotor(-angle);
		State.waitForSensorMotor();
	}
	
	private void moveDistanceTest(float distance, float speed) {
		Movement.moveDistance(distance, speed);
		State.waitForMotors(true, true);
		Movement.moveDistance(-distance, speed);
		State.waitForMotors(true, true);
		Movement.moveDistance(distance, -speed);
		State.waitForMotors(true, true);
		Movement.moveDistance(-distance, -speed);
		State.waitForMotors(true, true);
	}
	
	private void rotateTest(float angle, float speed) {
		Movement.rotate(angle, speed);
		State.waitForMotors(true, true);
		Movement.rotate(-angle, speed);
		State.waitForMotors(true, true);
		Movement.rotate(angle, -speed);
		State.waitForMotors(true, true);
		Movement.rotate(-angle, -speed); 
		State.waitForMotors(true, true);
	}
	

	private void combinedMovementTest1() {
		Movement.move(false, 10);
		Delay.msDelay(1000);
		Movement.rotateLeft(90, 10);
		Delay.msDelay(1000);
		Movement.rotateRight(90, 10);
		State.waitForMotors(true, true);
	}
	
	private void combinedMovementTest2() {
		Movement.move(false, 10);
		Delay.msDelay(1000);
		Movement.stop();
		Movement.rotateLeft(90, 10);
		State.waitForMovementMotors();
		Movement.rotateRight(90, 10);
		State.waitForMovementMotors();
		Movement.move(true, 10);
		Delay.msDelay(1000);
		Movement.stop();
	}
}

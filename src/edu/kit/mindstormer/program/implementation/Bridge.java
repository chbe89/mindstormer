package edu.kit.mindstormer.program.implementation;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.sensor.Sensor;

public class Bridge extends AbstractProgram {
	float sample;

	public Bridge() {
		super("Bridge");
	}
	
	public void run() {
		sample = 0;
		Movement.rotateSensorMotor(90);
		while (!State.stoppedSensor()) {
			
		}
		
		Movement.move(-200);
		while (sample < 4) {
			sample = Sensor.sampleDistance();
		}
		Movement.stop();
		Movement.rotateSensorMotor(-90);
		while (!State.stoppedSensor()) {
			
		}
		while (!quit.get()) {
		}
		
	}
}

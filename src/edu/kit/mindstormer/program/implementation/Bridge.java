package edu.kit.mindstormer.program.implementation;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.sensor.Sensor;

public class Bridge extends AbstractProgram {
	float sample;
	private static final int sensorRotation = 75;

	public Bridge() {
		super("Bridge");
	}
	
	public void run() {
		Movement.rotateSensorMotor(sensorRotation);
		while (!State.stoppedSensor()) {}
		
		while (!quit.get()) {
			sample = Sensor.sampleDistance();
			Movement.move(-24, -20);
			while (sample < 8f) {
				sample = Sensor.sampleDistance();
			}
			Movement.stop();
			Movement.rotate(20, 20);
			while (!State.stopped(true, true)) {}
		}
		
		Movement.stop();
		Movement.rotateSensorMotor(-sensorRotation);
		while (!State.stoppedSensor()) {}
	}
}

package edu.kit.mindstormer.program.implementation;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.sensor.Sensor;

public class Bridge extends AbstractProgram {
	float sample;
	private static final int sensorRotation = 75;
	private final int speed = -32;
	public Bridge() {
		super("Bridge");
	}
	
	public void run() {
		Movement.rotateSensorMotor(sensorRotation);
		while (!State.stoppedSensor()) {}
		
		while (!quit.get()) {
			sample = Sensor.sampleDistance();
			Movement.move(false, speed, false, speed - (speed / 5));
			int i = 0;
			while (i < 3) {
				sample = Sensor.sampleDistance();
				if (sample >= 8f) {
					i++;
				} else {
					i = 0;
				}
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

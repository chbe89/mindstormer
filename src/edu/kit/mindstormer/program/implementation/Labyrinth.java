package edu.kit.mindstormer.program.implementation;

import lejos.hardware.BrickFinder;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.utility.Delay;
import edu.kit.mindstormer.Constants;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.sensor.Sensor;

public class Labyrinth extends AbstractProgram {
	float sample;
	float sampleTouch;
	GraphicsLCD display;

	public Labyrinth() {
		super("Labyrinth");
		display = BrickFinder.getDefault().getGraphicsLCD();
		display.setFont(Font.getDefaultFont());
	}

	public void run() {

		while (!quit.get()) {
			sample = Sensor.sampleDistance();
			sampleTouch = Sensor.sampleTouchBoth();
			display.clear();
			display.drawString(String.valueOf(sample), display.getWidth() / 2, display.getHeight() / 2,
					GraphicsLCD.BASELINE | GraphicsLCD.HCENTER);
			Delay.msDelay(1000);

			display.clear();
			display.drawString("Touch" + String.valueOf(sampleTouch), display.getWidth() / 2, display.getHeight() / 2,
					GraphicsLCD.BASELINE | GraphicsLCD.HCENTER);
			Delay.msDelay(1000);
			Movement.move(200);
			while (Constants.MIN_WALL_DISTANCE < sample && sample < Constants.MAX_WALL_DISTANCE
					&& sampleTouch == Constants.TOUCH_SENSOR_UNPRESSED) {
				display.clear();
				display.drawString("MOVING", display.getWidth() / 2, display.getHeight() / 2,
						GraphicsLCD.BASELINE | GraphicsLCD.HCENTER);
				sample = Sensor.sampleDistance();
				sampleTouch = Sensor.sampleTouchBoth();
			}
			Movement.stop();
			if (sample < Constants.MIN_WALL_DISTANCE) {
				// correctAngleToWall
			} else if (sample >= Constants.MAX_WALL_DISTANCE && sampleTouch != Constants.BOTH_TOUCH_SENSOR_PRESSED) {
				Movement.rotate(-90, 250);
			} else {
				Movement.rotate(90, 250);
			}
			while (!State.stoppedSensor()) {
			}

		}

	}
}

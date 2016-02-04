package edu.kit.mindstormer.program.implementation;

import lejos.hardware.BrickFinder;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.utility.Delay;
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
	boolean turnLeft = false;
	while (!quit.get()) {
	    sample = Sensor.sampleDistance();
	    sampleTouch = Sensor.sampleTouch();
	    Movement.move(200);
	    while (0.1 < sample && sample < 0.3 && sampleTouch == 0) {
		sample = Sensor.sampleDistance();
		sampleTouch = Sensor.sampleTouch();
	    }
	    Movement.stop();
	    if (sample < 0.1) {
		// correctAngleToWall
	    } else if (sample >= 0.3 && sampleTouch != 1.0) {
		Movement.rotate(-90, 250);
	    } else {
		Movement.rotate(90, 250);
	    }
	    while (!State.stoppedSensor()) {
	    }

	}

    }
}

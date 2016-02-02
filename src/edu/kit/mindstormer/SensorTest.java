package edu.kit.mindstormer;

import java.util.concurrent.atomic.AtomicBoolean;

import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.Movement.Mode;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.Sound;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.Color;
import lejos.utility.Delay;

public class SensorTest {

    public static void main(String[] args) {
	// EV3GyroSensor gyro = new EV3GyroSensor(SensorPort.S2);
	// gyro.reset();
	// SampleProvider sensor = gyro.getRateMode();
	// float[] sample = new float[sensor.sampleSize()];
	EV3ColorSensor color = new EV3ColorSensor(SensorPort.S1);
	// color.setFloodlight(Color.WHITE);
	SensorMode sensor = color.getRedMode();
	float[] sample = new float[sensor.sampleSize()];
	GraphicsLCD g = BrickFinder.getDefault().getGraphicsLCD();

	int SW = g.getWidth();
	int SH = g.getHeight();

	Sound.beepSequenceUp();
	final AtomicBoolean stop = new AtomicBoolean(false);

	Button.ESCAPE.addKeyListener(new KeyListener() {

	    @Override
	    public void keyReleased(Key k) {
		stop.set(true);
	    }

	    @Override
	    public void keyPressed(Key k) {
		// TODO Auto-generated method stub

	    }
	});

	float lastValue = 0;
	while (!stop.get()) {
	    sensor.fetchSample(sample, 0);
	    Delay.msDelay(50);
	    g.clear();
	    g.setFont(Font.getDefaultFont());
	    g.drawString(String.valueOf(sample[0]) + " + " + String.valueOf(lastValue), SW / 2, SH / 2,
		    GraphicsLCD.BASELINE | GraphicsLCD.HCENTER);
/*
	    if (sample[0] > Constants.LINE_COLOR_THRESHOLD) {
		Movement.set(Mode.FORWARD, 50, Mode.FORWARD, 50);
	    } else {
		while (sample[0] <= Constants.LINE_COLOR_THRESHOLD) {
		    sensor.fetchSample(sample, 0);
		    Movement.rotateRight(20, true);
		    Movement.rotateLeft(40, true);
		}
	    }*/
	    lastValue = sample[0];
	    g.refresh();
	}
	// gyro.close();
	color.close();

	// Port port = LocalEV3.get().getPort("S2");
	// SensorModes sensor = new EV3UltrasonicSensor(port);
	// SampleProvider distance = sensor.getMode("Distance");
	// float[] sample = new float[sensor.sampleSize()];
	// GraphicsLCD g = BrickFinder.getDefault().getGraphicsLCD();
	//
	// int SW = g.getWidth();
	// int SH = g.getHeight();
	//
	// Sound.beepSequenceUp();
	// final AtomicBoolean stop = new AtomicBoolean(false);
	//
	// Button.ESCAPE.addKeyListener(new KeyListener() {
	//
	// @Override
	// public void keyReleased(Key k) {
	// stop.set(true);
	// }
	//
	// @Override
	// public void keyPressed(Key k) {
	// // TODO Auto-generated method stub
	//
	// }
	// });
	//
	// while (!stop.get()) {
	// Delay.msDelay(500);
	// g.clear();
	// distance.fetchSample(sample, 0);
	// g.setFont(Font.getDefaultFont());
	// g.drawString(String.valueOf(sample[0]), SW / 2, SH / 2,
	// GraphicsLCD.BASELINE | GraphicsLCD.HCENTER);
	// g.refresh();
	// }
    }

    private static void searchForLine() {
    }
}

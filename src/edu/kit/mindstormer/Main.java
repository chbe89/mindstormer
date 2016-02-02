package edu.kit.mindstormer;

import java.util.concurrent.atomic.AtomicBoolean;


import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class Main {


	final static AtomicBoolean escape = new AtomicBoolean(false);
	final static GraphicsLCD g = BrickFinder.getDefault().getGraphicsLCD();
	final static int SW = g.getWidth();
	final static int SH = g.getHeight();
	
    public static void main(String[] args) {
    
    	initEV3();
		initKeylisteners();
		listenToSensors();
		
		
		Sensors.closeSensors();
    }

    private static void initEV3() {
    	Button.LEDPattern(4);
    	Sound.beepSequenceUp();
    	g.setFont(Font.getDefaultFont());
    	g.drawString("Doge ready!", SW / 2, SH / 2, GraphicsLCD.BASELINE | GraphicsLCD.HCENTER);
    	g.refresh();
    	Sound.beepSequenceUp();
    }
    
    private static void listenToSensors() {
      	SampleProvider gyro = Sensors.getSensor("gyro", "S2", "angle");
    	float[] sample = new float[gyro.sampleSize()];

    	while (!escape.get()) {
    		sample = new float[gyro.sampleSize()];
    		gyro.fetchSample(sample, 0);
    	    Delay.msDelay(500);
    	    g.clear();
    	    g.setFont(Font.getDefaultFont());
    	    g.drawString(String.valueOf(sample[0]), SW / 2, SH / 2, GraphicsLCD.BASELINE | GraphicsLCD.HCENTER);
    	    g.refresh();
    	}
    }
    
	private static void initKeylisteners() {

		Button.ESCAPE.waitForPressAndRelease();
	}
}

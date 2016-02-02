package edu.kit.mindstormer;

import java.util.concurrent.atomic.AtomicBoolean;

import edu.kit.mindstormer.movement.Complex;
import edu.kit.mindstormer.movement.Movement;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.Sound;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;

public class Main {

    public static void main(String[] args) {
		GraphicsLCD g = BrickFinder.getDefault().getGraphicsLCD();
		int SW = g.getWidth();
		int SH = g.getHeight();
		Button.LEDPattern(4);
		Sound.beepSequenceUp();
		g.setFont(Font.getDefaultFont());
		g.drawString("Lejos EV3 - Prototype", SW / 2, SH / 2, GraphicsLCD.BASELINE | GraphicsLCD.HCENTER);
		g.refresh();
		Sound.beepSequenceUp();
	
		Complex.rotate(360, 100);
		Movement.init();
		final AtomicBoolean exit = new AtomicBoolean(false);
		
		Button.ESCAPE.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(Key k) {
				exit.set(true);
			}

			@Override
			public void keyReleased(Key k) {
				
			}
			
		});
		
		while(!exit.get()) {
			
		}
		
		
    }
}

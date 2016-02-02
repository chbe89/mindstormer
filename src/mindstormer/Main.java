package mindstormer;

import java.util.concurrent.atomic.AtomicBoolean;

import movement.Movement;
import movement.State;
import movement.Movement.Mode;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.CruizcoreGyro;
import lejos.hardware.sensor.SensorMode;
import lejos.utility.Delay;

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
	
	initKeylisteners();
	initSensorListeners();
	
	
    }

    private static void initSensorListeners() {
    	
    	
    }
    
	private static void initKeylisteners() {
		final AtomicBoolean forward = new AtomicBoolean(true);
		Button.UP.addKeyListener(new KeyListener() {

		    @Override
		    public void keyReleased(Key k) {
		    	Movement.setMode(Movement.Mode.BACKWARD);
		    	forward.set(false);
		    }

		    @Override
		    public void keyPressed(Key k) {
			// TODO Auto-generated method stub

		    }
		});
		
		Button.DOWN.addKeyListener(new KeyListener() {

		    @Override
		    public void keyReleased(Key k) {
		    	Movement.setMode(Movement.Mode.FORWARD);
		    	forward.set(true);
		    }

		    @Override
		    public void keyPressed(Key k) {
			// TODO Auto-generated method stub

		    }
		});
		Button.LEFT.addKeyListener(new KeyListener() {

		    @Override
		    public void keyReleased(Key k) {
				if (State.getModeLeft() != Mode.STOP) {
					Movement.setModeLeft(Mode.STOP);
				} else {
				    if (forward.get()) {
				    	Movement.setModeLeft(Mode.FORWARD);
				    } else {
				    	Movement.setModeLeft(Mode.BACKWARD);
				    }
				}
		    }

		    @Override
		    public void keyPressed(Key k) {
			// TODO Auto-generated method stub

		    }
		});
		Button.RIGHT.addKeyListener(new KeyListener() {

		    @Override
		    public void keyReleased(Key k) {
		    	if (State.getModeRight() != Mode.STOP) {
					Movement.setModeRight(Mode.STOP);
				} else {
				    if (forward.get()) {
				    	Movement.setModeRight(Mode.FORWARD);
				    } else {
				    	Movement.setModeRight(Mode.BACKWARD);
				    }
				}
		    }

		    @Override
		    public void keyPressed(Key k) {
			// TODO Auto-generated method stub

		    }
		});

		Button.ENTER.addKeyListener(new KeyListener() {

		    @Override
		    public void keyReleased(Key k) {
				if(State.getModeLeft() != Mode.STOP || State.getModeRight() != Mode.STOP){
					Movement.setMode(Mode.STOP);
				} else {
					Movement.setMode(Mode.FORWARD);
				}
		    }

		    @Override
		    public void keyPressed(Key k) {
			// TODO Auto-generated method stub

		    }
		});
		
		Button.ESCAPE.waitForPressAndRelease();
	}
}

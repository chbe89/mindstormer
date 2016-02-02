package mindstormer;

import java.util.concurrent.atomic.AtomicBoolean;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.Sound;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import movement.Movement;
import movement.Movement.Mode;
import movement.Sensors;
import movement.State;

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
				
				escape.set(true);
		    }

		    @Override
		    public void keyPressed(Key k) {
			// TODO Auto-generated method stub

		    }
		});
		
		Button.ESCAPE.waitForPressAndRelease();
	}
}

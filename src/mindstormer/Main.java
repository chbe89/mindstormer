package mindstormer;

import java.util.concurrent.atomic.AtomicBoolean;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.Sound;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;

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
	final AtomicBoolean forward = new AtomicBoolean(true);
	Sound.beepSequenceUp();

	final NXTRegulatedMotor left = Motor.A;
	final NXTRegulatedMotor right = Motor.D;
	Motor.A.setSpeed(960);// 2 RPM
	Motor.D.setSpeed(960);// 2 RPM
	Button.DOWN.addKeyListener(new KeyListener() {

	    @Override
	    public void keyReleased(Key k) {
		left.backward();
		right.backward();
		forward.set(false);
	    }

	    @Override
	    public void keyPressed(Key k) {
		// TODO Auto-generated method stub

	    }
	});
	Button.UP.addKeyListener(new KeyListener() {

	    @Override
	    public void keyReleased(Key k) {
		left.forward();
		right.forward();
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
		if (left.isMoving()) {
		    left.stop();
		} else {
		    if (forward.get()) {
			left.forward();
		    } else {
			left.backward();
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
		if (right.isMoving()) {
		    right.stop();
		} else {
		    if (forward.get()) {
			right.forward();
		    } else {
			right.backward();
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
		if(left.isMoving() || right.isMoving()){
		    left.stop();
		    right.stop();
		} else {
		    right.forward();
		    left.forward();
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

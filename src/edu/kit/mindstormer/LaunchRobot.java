package edu.kit.mindstormer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

import edu.kit.mindstormer.program.OperatingSystem;
import edu.kit.mindstormer.program.Program;
import edu.kit.mindstormer.program.implementation.test.NavigatorProgram;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.Sound;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.utility.Delay;

public class LaunchRobot {

	final static AtomicBoolean escape = new AtomicBoolean(false);
	final static GraphicsLCD g = BrickFinder.getDefault().getGraphicsLCD();
	final static int SW = g.getWidth();
	final static int SH = g.getHeight();
	
	public static void main(String[] args) {
		
		initEV3();
		Button.ESCAPE.addKeyListener(new KeyListener() {
		    @Override
		    public void keyReleased(Key k) {
		    	escape.set(true);
		    }

		    @Override
		    public void keyPressed(Key k) {
		    }
		});
		
		
    	while (!escape.get()) {
    	    Delay.msDelay(500);
    	}
    	
    	
		Collection<Program> programs = new ArrayList<Program>();
		programs.add(new NavigatorProgram());
		
		OperatingSystem os = OperatingSystem.withPrograms(programs);
		os.run();
		
	}

    private static void initEV3() {
    	Button.LEDPattern(4);
    	Sound.beepSequenceUp();
    	g.setFont(Font.getDefaultFont());
    	g.drawString("Doge ready!", SW / 2, SH / 2, GraphicsLCD.BASELINE | GraphicsLCD.HCENTER);
    	g.refresh();
    	Sound.beepSequenceUp();
    }
}

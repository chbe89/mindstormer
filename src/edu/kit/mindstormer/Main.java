package edu.kit.mindstormer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

import edu.kit.mindstormer.program.OperatingSystem;
import edu.kit.mindstormer.program.Program;
import edu.kit.mindstormer.program.implementation.*;
import lejos.hardware.BrickFinder;
import lejos.hardware.lcd.GraphicsLCD;

public class Main {


	final static AtomicBoolean escape = new AtomicBoolean(false);
	final static GraphicsLCD g = BrickFinder.getDefault().getGraphicsLCD();
	final static int SW = g.getWidth();
	final static int SH = g.getHeight();
	
    public static void main(String[] args) {
    
		Collection<Program> programs = new ArrayList<Program>();
		programs.add(new NavigatorProgram());
		programs.add(new FollowLine());
		programs.add(new Seesaw());
		programs.add(new Bridge());
		programs.add(new RollerBox());
		programs.add(new MoveFixedDistance());
		
		OperatingSystem os = OperatingSystem.withPrograms(programs);
		os.run();
    }
}

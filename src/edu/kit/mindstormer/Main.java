package edu.kit.mindstormer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

import edu.kit.mindstormer.program.OperatingSystem;
import edu.kit.mindstormer.program.Program;
import edu.kit.mindstormer.program.implementation.*;
import edu.kit.mindstormer.program.implementation.test.BlockingTest;
import edu.kit.mindstormer.program.implementation.test.DistanceSensorTest;
import edu.kit.mindstormer.program.implementation.test.MoveFixedDistance;
import edu.kit.mindstormer.program.implementation.test.NavigatorProgram;
import lejos.hardware.BrickFinder;
import lejos.hardware.lcd.GraphicsLCD;

public class Main {


	final static AtomicBoolean escape = new AtomicBoolean(false);
	final static GraphicsLCD g = BrickFinder.getDefault().getGraphicsLCD();
	final static int SW = g.getWidth();
	final static int SH = g.getHeight();
	
    public static void main(String[] args) {
    
		Collection<Program> programs = new ArrayList<Program>();
		
		programs.add(new FollowLine());
		programs.add(new Bridge());
		programs.add(new Seesaw());
		programs.add(new RollerBox());
		programs.add(new MoveFixedDistance());
		programs.add(new DistanceSensorTest());
		programs.add(new BlockingTest());
		programs.add(new NavigatorProgram());
		
		OperatingSystem os = OperatingSystem.withPrograms(programs);
		os.run();
    }
}

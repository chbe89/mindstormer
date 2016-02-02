package edu.kit.mindstormer;

import java.util.ArrayList;
import java.util.Collection;

import edu.kit.mindstormer.program.OperatingSystem;
import edu.kit.mindstormer.program.Program;
import edu.kit.mindstormer.program.implementation.NavigatorProgram;

public class LaunchRobot {

	public static void main(String[] args) {
		Collection<Program> programs = new ArrayList<Program>();
		programs.add(new NavigatorProgram());
		
		OperatingSystem os = OperatingSystem.withPrograms(programs);
		Thread t = new Thread(os);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
		}
	}

}

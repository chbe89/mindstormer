package edu.kit.mindstormer.program;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lejos.hardware.Button;

public class OperatingSystem implements ProgramContext {

	private final List<Program> programs;
	private final OsKeyListener navigationKeyListener;
	private int pc = 0;
	
	private OperatingSystem(List<Program> programs) {
		this.programs = programs;
		this.navigationKeyListener = new OsKeyListener(this);
	}

	public static OperatingSystem withPrograms(Collection<Program> programs) {
		return new OperatingSystem(new ArrayList<Program>(programs));
	}

	public void start() {
		Button.DOWN.addKeyListener(navigationKeyListener);
		Button.UP.addKeyListener(navigationKeyListener);
		Button.ENTER.addKeyListener(navigationKeyListener);
		
		navigationKeyListener.deactivate();
		// start program
		navigationKeyListener.activate();
	}

}

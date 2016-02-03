package edu.kit.mindstormer.program;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.sensor.Sensor;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.utility.Delay;

public class OperatingSystem implements ProgramContext {

	private final List<Program> programs;

	private final OsKeyListener navigationKeyListener;

	private static GraphicsLCD display;
	private static int width;
	private static int height;

	private int pc = 0;
	
	private final AtomicBoolean quitOS = new AtomicBoolean(false);
	private final ArrayList<Program> runningPrograms = new ArrayList<>();
	
	public static OperatingSystem withPrograms(Collection<Program> programs) {
		Movement.init();
		Sensor.init();
		return new OperatingSystem(new ArrayList<Program>(programs));
	}

	private OperatingSystem(List<Program> programs) {
		this.programs = programs;
		this.navigationKeyListener = new OsKeyListener(this);
		display = initializeDisplay();
		width = display.getWidth();
		height = display.getHeight();
	}

	private GraphicsLCD initializeDisplay() {
		GraphicsLCD display = BrickFinder.getDefault().getGraphicsLCD();
		display.setFont(Font.getDefaultFont());
		return display;
	}

	public void run() {
		displayText("Started OS! " + Button.UP.getClass().getSimpleName());
		Sound.beepSequenceUp();
		Button.DOWN.addKeyListener(navigationKeyListener);
		Button.UP.addKeyListener(navigationKeyListener);
		Button.ENTER.addKeyListener(navigationKeyListener);
		Button.ESCAPE.addKeyListener(navigationKeyListener);

		while (!quitOS.get()) {
			if (runningPrograms.size() > 0) {
				Program program = runningPrograms.remove(0);
				startProgram(program);
			}
		}
	}

	@Override
	public void terminateProgram() {
		//programs.get(pc).terminate();
		displayText("Trying to stop program");
		AbstractProgram.quit.set(true);
		navigationKeyListener.activate();
	}

	@Override
	public void terminateOs() {
		displayText("Terminating OS, Goodbye");
		quitOS.set(true);
	}

	@Override
	public void startProgram(Program program) {
		navigationKeyListener.deactivate();
		Program.quit.set(false);
		displayText(program.getName() + " running");
		program.run();
		displayText(program.getName() + " stopped");
		Delay.msDelay(500);
		navigationKeyListener.activate();
		displayCurrentProgram();
	}

	@Override
	public void addProgramToQueue() {
		Program program = programs.get(pc);
		runningPrograms.add(program);
	}
	
	@Override
	public void showNextProgram() {
		pc = (pc + 1) % programs.size();
		displayCurrentProgram();
	}

	private void displayCurrentProgram() {
		Program program = programs.get(pc);
		if (program == null)
			return;

		displayText(program.getName());
	}

	public static void displayText(String text) {
		display.clear();
		display.drawString(text, width / 2, height / 2, GraphicsLCD.BASELINE
				| GraphicsLCD.HCENTER);
		//display.refresh();
	}

	@Override
	public void showPreviousProgram() {
		pc = ((pc - 1) + programs.size()) % programs.size();
		displayCurrentProgram();
	}
}

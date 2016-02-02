package edu.kit.mindstormer.program;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;

public class OperatingSystem implements ProgramContext, Runnable {

	private final List<Program> programs;

	private final OsKeyListener navigationKeyListener;
	private final CountDownLatch latch;

	private final GraphicsLCD display;
	private final int width;
	private final int height;

	private int pc = 0;

	public static OperatingSystem withPrograms(Collection<Program> programs) {
		return new OperatingSystem(new ArrayList<Program>(programs));
	}

	private OperatingSystem(List<Program> programs) {
		this.programs = programs;
		this.navigationKeyListener = new OsKeyListener(this);
		this.latch = new CountDownLatch(1);
		this.display = initializeDisplay();
		this.width = display.getWidth();
		this.height = display.getHeight();
	}

	private GraphicsLCD initializeDisplay() {
		GraphicsLCD display = BrickFinder.getDefault().getGraphicsLCD();
		display.setFont(Font.getDefaultFont());
		return display;
	}

	@Override
	public void run() {
		Button.DOWN.addKeyListener(navigationKeyListener);
		Button.UP.addKeyListener(navigationKeyListener);
		Button.ENTER.addKeyListener(navigationKeyListener);
		Button.ESCAPE.addKeyListener(navigationKeyListener);

		try {
			latch.await();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	@Override
	public void terminateProgram() {
		programs.get(pc).terminate();
		navigationKeyListener.activate();
	}

	@Override
	public void terminateOs() {
		navigationKeyListener.deactivate();
		latch.countDown();
	}

	@Override
	public void startProgram() {
		Program program = programs.get(pc);
		program.run();

		if (!navigationKeyListener.isActive())
			navigationKeyListener.activate();
	}

	@Override
	public void showNextProgram() {
		pc = (pc + 1) % programs.size();
		displayCurrentProgram();
	}

	private void displayCurrentProgram() {
		Program program = programs.get(pc);
		display.drawString(program.getName(), width / 2, height / 2,
				GraphicsLCD.BASELINE | GraphicsLCD.HCENTER);
		display.refresh();
	}

	@Override
	public void showPreviousProgram() {
		pc = (pc - 1) % programs.size();
		displayCurrentProgram();
	}
}

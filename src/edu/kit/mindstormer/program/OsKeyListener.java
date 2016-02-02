package edu.kit.mindstormer.program;

import java.util.concurrent.atomic.AtomicBoolean;

import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;

class OsKeyListener implements KeyListener {

	private final AtomicBoolean isActive = new AtomicBoolean(true);
	private final ProgramContext context;

	OsKeyListener(ProgramContext context) {
		this.context = context;
	}

	public final void activate() {
		isActive.set(true);
	}

	public final void deactivate() {
		isActive.set(false);
	}

	public boolean isActive() {
		return isActive.get();
	}

	@Override
	public void keyPressed(Key k) {
		// do nothing
	}

	@Override
	public void keyReleased(Key k) {
		if (Button.UP.equals(k)) {
			if (isActive())
				context.showPreviousProgram();
		} else if (Button.DOWN.equals(k)) {
			if (isActive())
				context.showNextProgram();
		} else if (Button.ENTER.equals(k)) {
			if (isActive())
				context.addProgramToQueue();
		} else if (Button.ESCAPE.equals(k)) {
			if (!isActive())
				context.terminateProgram();
			else
				context.terminateOs();
		}
	}
}

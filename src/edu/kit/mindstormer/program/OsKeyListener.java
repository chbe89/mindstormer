package edu.kit.mindstormer.program;

import java.util.concurrent.atomic.AtomicBoolean;

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
		isActive.set(true);
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
		switch (k.getId()) {
		case Key.UP:
			if (isActive())
				context.showPreviousProgram();
			break;
		case Key.DOWN:
			if (isActive())
				context.showNextProgram();
			break;
		case Key.ENTER:
			if (isActive())
				context.startProgram();
			break;
		case Key.ESCAPE:
			if (!isActive())
				context.terminateProgram();
			else
				context.terminateOs();
			break;
		}
	}
}

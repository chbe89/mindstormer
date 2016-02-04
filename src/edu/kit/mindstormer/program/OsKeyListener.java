package edu.kit.mindstormer.program;

import java.util.concurrent.atomic.AtomicBoolean;

import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import edu.kit.mindstormer.util.SmartKey;
import edu.kit.mindstormer.util.TimePad;

class OsKeyListener implements KeyListener {

	private final AtomicBoolean isActive = new AtomicBoolean(true);
	private final ProgramContext context;

	private final TimePad pad = new TimePad();

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
		if (!pad.requestTime())
			return;

		SmartKey key = SmartKey.from(k);
		switch (key) {
		case DOWN:
			if (isActive())
				context.showNextProgram();
			break;
		case ENTER:
			if (isActive())
				context.addProgramToQueue();
			break;
		case ESCAPE:
			if (!isActive())
				context.terminateProgram();
			else
				context.terminateOs();
			break;
		case LEFT:
			shutdown();
			break;
		case RIGHT:
			shutdown();
			break;
		case UP:
			if (isActive())
				context.showPreviousProgram();
			break;
		}
	}

	private void shutdown() {
		context.terminateOs();
		OperatingSystem.displayText("Trying to shutdown OS");
	}
}

package edu.kit.mindstormer.util;

import java.util.concurrent.atomic.AtomicBoolean;

import lejos.hardware.Key;
import lejos.hardware.KeyListener;

public abstract class AbstractStateDependentKeyListener implements KeyListener {

	private final AtomicBoolean isActive = new AtomicBoolean(true);

	public final void activate() {
		isActive.set(true);
	}

	public final void deactivate() {
		isActive.set(true);
	}

	@Override
	public final void keyPressed(Key k) {
		if (isActive.get())
			pressed(k);
	}

	public abstract void pressed(Key k);

	@Override
	public final void keyReleased(Key k) {
		if (isActive.get())
			released(k);
	}

	public abstract void released(Key k);
}

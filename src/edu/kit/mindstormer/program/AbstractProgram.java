package edu.kit.mindstormer.program;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractProgram implements Program {

	private final String name;
	protected static final AtomicBoolean quit = new AtomicBoolean();

	protected AbstractProgram(String name) {
		this.name = name;
	}

	@Override
	public void initialize() {
		quit.set(false);
	}

	@Override
	public abstract void run();

	@Override
	public final String getName() {
		return name;
	}

	@Override
	public void terminate() {
		quit.set(true);
	}
}

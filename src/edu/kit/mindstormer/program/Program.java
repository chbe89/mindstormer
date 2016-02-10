package edu.kit.mindstormer.program;

import java.util.concurrent.atomic.AtomicBoolean;

public interface Program extends Comparable<Program> {

	public static final AtomicBoolean quit = new AtomicBoolean();

	public String getName();

	public void initialize();

	public void run();

	public void terminate();
}

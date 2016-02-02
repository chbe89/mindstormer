package edu.kit.mindstormer.program;

public interface Program extends Runnable {
	
	public String getName();
	public void initialize();
	@Override
	public void run();
	public void terminate();
}

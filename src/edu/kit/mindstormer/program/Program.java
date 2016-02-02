package edu.kit.mindstormer.program;

public interface Program {
	
	public String getName();
	public void initialize();
	public void run();
	public void terminate();
}

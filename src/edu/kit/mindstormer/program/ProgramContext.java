package edu.kit.mindstormer.program;

public interface ProgramContext {

	public void terminateProgram();
	public void terminateOs();
	public void startProgram(Program program);
	public void addProgramToQueue();
	public void showNextProgram();
	public void showPreviousProgram();
}

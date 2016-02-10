package edu.kit.mindstormer.program;


public abstract class AbstractProgram implements Program {

	private final String name;

	protected AbstractProgram() {
		this.name = this.getClass().getSimpleName();
	}
	
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
	
	@Override
	public int compareTo(Program o) {
		return name.compareTo(o.getName());
	}

	@Override
	public String toString() {
		return "Program [" + name + "]";
	}
}

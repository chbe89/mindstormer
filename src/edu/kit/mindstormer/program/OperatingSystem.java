package edu.kit.mindstormer.program;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OperatingSystem {

	private final List<Program> programs;

	private OperatingSystem(List<Program> programs) {
		this.programs = programs;
	}

	public static OperatingSystem withPrograms(Collection<Program> programs) {
		return new OperatingSystem(new ArrayList<Program>(programs));
	}

	public void start() {

	}

}

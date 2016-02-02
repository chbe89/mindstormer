package edu.kit.mindstormer.program;

import lejos.hardware.Key;
import edu.kit.mindstormer.util.AbstractStateDependentKeyListener;

class OsKeyListener extends AbstractStateDependentKeyListener {

	private final ProgramContext context;

	OsKeyListener(ProgramContext context) {
		this.context = context;
	}

	@Override
	public void pressed(Key k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void released(Key k) {
		// TODO Auto-generated method stub

	}

}

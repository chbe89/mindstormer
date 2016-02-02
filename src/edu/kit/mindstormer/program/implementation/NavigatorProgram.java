package edu.kit.mindstormer.program.implementation;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.Movement.Mode;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.util.AbstractStateDependentKeyListener;
import lejos.hardware.Button;
import lejos.hardware.Key;

public class NavigatorProgram extends AbstractProgram {

	ArrayList<AbstractStateDependentKeyListener> listeners;
	protected NavigatorProgram() {
		super("Navigator");
	}

	@Override
	public void run() {
		initListeners();
		while (!quit.get()) {
			
		}
		deactivateListeners();
	}

	private void initListeners() {
		final AtomicBoolean forward = new AtomicBoolean(true);
		AbstractStateDependentKeyListener up = new AbstractStateDependentKeyListener() {
		    @Override
		    public void released(Key k) {
		    	Movement.move(-500, -500);
		    	forward.set(false);
		    }
		    @Override
		    public void pressed(Key k) {
		    }
		};
		Button.UP.addKeyListener(up);
		listeners.add(up);
		
		AbstractStateDependentKeyListener down = new AbstractStateDependentKeyListener() {
		    @Override
		    public void released(Key k) {
		    	Movement.move(500, 500);
		    	forward.set(true);
		    }

		    @Override
		    public void pressed(Key k) {
		    }
		};
		Button.DOWN.addKeyListener(down);
		listeners.add(down);
		
		AbstractStateDependentKeyListener left = new AbstractStateDependentKeyListener() {
		    @Override
		    public void released(Key k) {
				/*if (State.getModeLeft() != Mode.STOP) {
					Movement.setModeLeft(Mode.STOP);
				} else {
				    if (forward.get()) {
				    	Movement.setModeLeft(Mode.FORWARD);
				    } else {
				    	Movement.setModeLeft(Mode.BACKWARD);
				    }
				}*/
		    }

		    @Override
		    public void pressed(Key k) {
		    }
		};
		Button.LEFT.addKeyListener(left);
		listeners.add(left);
		
		AbstractStateDependentKeyListener right = new AbstractStateDependentKeyListener() {
		    @Override
		    public void released(Key k) {
		    	/*if (State.getModeRight() != Mode.STOP) {
					Movement.setModeRight(Mode.STOP);
				} else {
				    if (forward.get()) {
				    	Movement.setModeRight(Mode.FORWARD);
				    } else {
				    	Movement.setModeRight(Mode.BACKWARD);
				    }
				}*/
		    }

		    @Override
		    public void pressed(Key k) {
			// TODO Auto-generated method stub

		    }
		};
		Button.RIGHT.addKeyListener(right);
		listeners.add(right);
		
		AbstractStateDependentKeyListener enter = new AbstractStateDependentKeyListener() {
		    @Override
		    public void released(Key k) {
				/*if(State.getModeLeft() != Mode.STOP || State.getModeRight() != Mode.STOP){
					Movement.setMode(Mode.STOP);
				} else {
					Movement.setMode(Mode.FORWARD);
				}*/
		    }
		    @Override
		    public void pressed(Key k) {
			// TODO Auto-generated method stub

		    }
		};
		Button.ENTER.addKeyListener(enter);
		listeners.add(enter);
	}
	/*
	private void activateListeners() {
		for (AbstractStateDependentKeyListener listener : listeners) {
			listener.activate();
		}
	}
	*/
	private void deactivateListeners() {
		for (AbstractStateDependentKeyListener listener : listeners) {
			listener.deactivate();
		}
	}
}

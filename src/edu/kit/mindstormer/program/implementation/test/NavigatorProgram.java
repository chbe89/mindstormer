package edu.kit.mindstormer.program.implementation.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.util.AbstractStateDependentKeyListener;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;

public class NavigatorProgram extends AbstractProgram {

	List<AbstractStateDependentKeyListener> listeners = new ArrayList<>();
	public NavigatorProgram() {
		super("Navigator");
	}

	@Override
	public void run() {
		initListeners();
		
		GraphicsLCD display = BrickFinder.getDefault().getGraphicsLCD();
		display.setFont(Font.getDefaultFont());
		
		int width = display.getWidth();
		int height = display.getHeight();
		
		while (!quit.get()) {
			display.clear();
			display.drawString("NavigatorProgram running", width / 2, height / 2, GraphicsLCD.BASELINE
					| GraphicsLCD.HCENTER);
			display.refresh();
		}
		
		display.clear();
		display.drawString("NavigatorProgram shutting down", width / 2, height / 2, GraphicsLCD.BASELINE
				| GraphicsLCD.HCENTER);
		
		deactivateListeners();
		Movement.stop();
	}

	private void initListeners() {
		final AtomicBoolean forward = new AtomicBoolean(true);
		AbstractStateDependentKeyListener up = new AbstractStateDependentKeyListener() {
		    @Override
		    public void released(Key k) {
		    	Movement.move(true, 20);
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
		    	Movement.move(false, 20);
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

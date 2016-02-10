package edu.kit.mindstormer.program.implementation;

import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.sensor.Sensor;

public class Parkours extends AbstractProgram {
	float sample;
	private static final int sensorRotation = 75;

	final int skip;

	public Parkours(int skip) {
		super(getName(skip));
		this.skip = skip;
	}

	private static String getName(int skip) {
		switch (skip) {
		case 0:
			return "Parkour ganz";
		case 1:
			return "Parkour ab Bridge";
		case 2:
			return "Parkour ab LineFollow";
		case 3:
			return "Parkour ab Seesaw";
		case 4:
			return "Parkour ab LineFollow2";
		case 5:
			return "Parkour ab ChainBridge";
		case 6:
			return "Parkour ab RollerBox";
		case 7:
			return "Parkour ab Race";
		}
		return "Parkour undefined";
	}

	public void run() {

		outer:
		while (!quit.get()) {
			switch (skip) {
			case 0:
				new Labyrinth().run();
				if (!quit.get())
					break outer;
			case 1:
				new ChainBridge().run();
				if (!quit.get())
					break outer;
			case 2:	
				new FollowLine().run();
				if (!quit.get())
					break outer;
			case 3:
				new Seesaw().run();
				if (!quit.get())
					break outer;
			case 4:
				new FollowLine().run();
				if (!quit.get())
					break outer;
			case 5:
				new ChainBridge().run();
				if (!quit.get())
					break outer;
			case 6:
				new RollerBox().run();
				if (!quit.get())
					break outer;
			case 7:
				new Race().run();
				if (!quit.get())
					break outer;
			case 8:
				// new Endboss().run();
				if (!quit.get())
					break outer;
			default:
				break outer;
			}
			

		}

	}
}
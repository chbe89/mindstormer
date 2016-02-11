package edu.kit.mindstormer.program.implementation;

import edu.kit.mindstormer.program.AbstractProgram;

public class Parkours2 extends AbstractProgram {
	float sample;

	final int skip;

	public Parkours2(int skip) {
		super(getName(skip));
		this.skip = skip;
	}

	private static String getName(int skip) {
		switch (skip) {
		case 0:
			return "P2 ganz";
		case 1:
			return "P2 ab Bridge2";
		case 2:
			return "P2 ab FollowLineLift2";
		case 3:
			return "P2 ab Seesaw";
		case 4:
			return "P2 ab ChainBridge";
		case 5:
			return "P2 ab RollerBox";
		case 6:
			return "P2 ab Race";
		case 7:
			return "P2 ab Endboss";
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
				new Bridge2().run();
				if (!quit.get())
					break outer;
			case 2:	
				new FollowLineLiftToSeesaw2().run();
				if (!quit.get())
					break outer;
			case 3:
				new Seesaw().run();
				if (!quit.get())
					break outer;
			case 4:
				new ChainBridge().run();
				if (!quit.get())
					break outer;
			case 5:
				new RollerBox().run();
				if (!quit.get())
					break outer;
			case 6:
				new Race().run();
				if (!quit.get())
					break outer;
			case 7:
				new Endboss().run();
				if (!quit.get())
					break outer;
			default:
				break outer;
			}
			

		}

	}
}

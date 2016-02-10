package edu.kit.mindstormer.program.implementation;

import edu.kit.mindstormer.program.AbstractProgram;

public class Parkours extends AbstractProgram {
	float sample;

	final int skip;

	public Parkours(int skip) {
		super(getName(skip));
		this.skip = skip;
	}

	private static String getName(int skip) {
		switch (skip) {
		case 0:
			return "P ganz";
		case 1:
			return "P ab Bridge";
		case 2:
			return "P ab FollowLineLift";
		case 3:
			return "P ab Seesaw";
		case 4:
			return "P ab ChainBridge";
		case 5:
			return "P ab RollerBox";
		case 6:
			return "P ab Race";
		case 7:
			return "P ab Endboss";
		}
		return "Parkour undefined";
	}

	public void run() {

		outer:
		while (!quit.get()) {
			switch (skip) {
			case 0:
				new Labyrinth().startProgram();
				if (!quit.get())
					break outer;
			case 1:
				new Bridge().startProgram();
				if (!quit.get())
					break outer;
			case 2:	
				new FollowLineLiftToSeesaw().startProgram();
				if (!quit.get())
					break outer;
			case 3:
				new Seesaw().startProgram();
				if (!quit.get())
					break outer;
			case 4:
				new ChainBridge().startProgram();
				if (!quit.get())
					break outer;
			case 5:
				new RollerBox().startProgram();
				if (!quit.get())
					break outer;
			case 6:
				new Race().startProgram();
				if (!quit.get())
					break outer;
			case 7:
				new Endboss().startProgram();
				if (!quit.get())
					break outer;
			default:
				break outer;
			}
			

		}

	}
}

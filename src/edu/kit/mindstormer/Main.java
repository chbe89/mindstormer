package edu.kit.mindstormer;

import java.util.ArrayList;
import java.util.List;

import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.program.OperatingSystem;
import edu.kit.mindstormer.program.Program;
import edu.kit.mindstormer.program.implementation.AfterBox;
import edu.kit.mindstormer.program.implementation.AfterChain;
import edu.kit.mindstormer.program.implementation.Bridge2;
import edu.kit.mindstormer.program.implementation.ChainBridge;
import edu.kit.mindstormer.program.implementation.Endboss;
import edu.kit.mindstormer.program.implementation.FollowLine;
import edu.kit.mindstormer.program.implementation.FollowLineLiftToSeesaw;
import edu.kit.mindstormer.program.implementation.Labyrinth;
import edu.kit.mindstormer.program.implementation.Parkours;
import edu.kit.mindstormer.program.implementation.Parkours2;
import edu.kit.mindstormer.program.implementation.Race;
import edu.kit.mindstormer.program.implementation.RollerBox;
import edu.kit.mindstormer.program.implementation.Seesaw;
import edu.kit.mindstormer.program.implementation.test.NavigatorProgram;
import edu.kit.mindstormer.program.implementation.test.ReadjustSensor;
import edu.kit.mindstormer.sensor.Sensor;

public class Main {

	private static final List<Program> programs = new ArrayList<Program>();

	public static void main(String[] args) {
		initHardware();
		installPrograms();

		OperatingSystem os = OperatingSystem.withPrograms(programs);
		os.run();
	}

	private static void initHardware() {
		Movement.init();
		Sensor.init();
	}

	private static void installPrograms() {
		programs.clear();
		programs.add(new Labyrinth());
		programs.add(new Bridge2());
		programs.add(new FollowLineLiftToSeesaw());
		//programs.add(new FollowLineBasic());
		programs.add(new Seesaw());
		programs.add(new ChainBridge());
		programs.add(new RollerBox());
		programs.add(new Race());
		programs.add(new Endboss());
		programs.add(new NavigatorProgram());
		programs.add(new ReadjustSensor());
		programs.add(new FollowLine());
		
		for (int i = 0; i < 8; i++) {
			programs.add(new Parkours(i));
		}
		
		for (int i = 0; i < 8; i++) {
			programs.add(new Parkours2(i));
		}
		
		programs.add(new AfterBox());
		programs.add(new AfterChain());
		
		/*
		 * programs.add(new Parkours(0)); programs.add(new Parkours(1));
		 * programs.add(new Parkours(2)); programs.add(new Parkours(3));
		 * programs.add(new Parkours(4)); programs.add(new Parkours(5));
		 * programs.add(new Parkours(6)); programs.add(new Parkours(7));
		 */
	}
}

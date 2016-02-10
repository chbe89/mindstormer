package edu.kit.mindstormer;

import java.util.ArrayList;
import java.util.Collection;

import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.program.OperatingSystem;
import edu.kit.mindstormer.program.Program;
import edu.kit.mindstormer.program.implementation.Bridge;
import edu.kit.mindstormer.program.implementation.ChainBridge;
import edu.kit.mindstormer.program.implementation.Endboss;
import edu.kit.mindstormer.program.implementation.FollowLine;
import edu.kit.mindstormer.program.implementation.FollowLineAndStop;
import edu.kit.mindstormer.program.implementation.FollowLineLiftToSeesaw;
import edu.kit.mindstormer.program.implementation.Labyrinth;
import edu.kit.mindstormer.program.implementation.Race;
import edu.kit.mindstormer.program.implementation.RollerBox;
import edu.kit.mindstormer.program.implementation.Seesaw;
import edu.kit.mindstormer.program.implementation.test.BlockingTest;
import edu.kit.mindstormer.program.implementation.test.ColorSampler;
import edu.kit.mindstormer.program.implementation.test.MovementTest;
import edu.kit.mindstormer.program.implementation.test.NavigatorProgram;
import edu.kit.mindstormer.program.implementation.test.ReadjustSensor;
import edu.kit.mindstormer.sensor.Sensor;

public class Main {

	private static final Collection<Program> programs = new ArrayList<Program>();

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
		programs.add(new FollowLineLiftToSeesaw());
		programs.add(new ChainBridge());
		programs.add(new Race());
		programs.add(new BlockingTest());
		programs.add(new MovementTest());
		programs.add(new FollowLine());
		programs.add(new FollowLineAndStop());
		programs.add(new Bridge());
		programs.add(new Seesaw());
		programs.add(new RollerBox());
		programs.add(new NavigatorProgram());
		programs.add(new ReadjustSensor());
		programs.add(new ColorSampler());
		programs.add(new Endboss());

		/*
		 * programs.add(new Parkours(0)); programs.add(new Parkours(1));
		 * programs.add(new Parkours(2)); programs.add(new Parkours(3));
		 * programs.add(new Parkours(4)); programs.add(new Parkours(5));
		 * programs.add(new Parkours(6)); programs.add(new Parkours(7));
		 */
	}
}

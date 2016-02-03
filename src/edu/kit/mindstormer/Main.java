package edu.kit.mindstormer;

import java.util.ArrayList;
import java.util.Collection;

import edu.kit.mindstormer.program.OperatingSystem;
import edu.kit.mindstormer.program.Program;
import edu.kit.mindstormer.program.implementation.Bridge;
import edu.kit.mindstormer.program.implementation.FollowLine;
import edu.kit.mindstormer.program.implementation.RollerBox;
import edu.kit.mindstormer.program.implementation.Seesaw;
import edu.kit.mindstormer.program.implementation.test.BlockingTest;
import edu.kit.mindstormer.program.implementation.test.DistanceSensorTest;
import edu.kit.mindstormer.program.implementation.test.MoveFixedDistance;
import edu.kit.mindstormer.program.implementation.test.NavigatorProgram;

public class Main {

    public static void main(String[] args) {
		Collection<Program> programs = new ArrayList<Program>();
		programs.add(new NavigatorProgram());
		programs.add(new FollowLine());
		programs.add(new Seesaw());
		programs.add(new Bridge());
		programs.add(new RollerBox());
		programs.add(new MoveFixedDistance());
		programs.add(new DistanceSensorTest());
		programs.add(new BlockingTest());
		
		OperatingSystem os = OperatingSystem.withPrograms(programs);
		os.run();
    }
}

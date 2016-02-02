package edu.kit.mindstormer.level;

import java.util.Map;

import edu.kit.mindstormer.barcode.Barcode;
import edu.kit.mindstormer.movement.Movement;

import java.util.HashMap;

public class Controller {
	private Map<Barcode, Level> levels;
	private Level initialLevel;
	private Level currentLevel;
	
	public Controller() {
		levels = new HashMap<>();
		levels.put(new Barcode(), new LevelFollowLine());
	}
	
	public void barcodeFound(Barcode barcode) {
		switchLevels(levels.get(barcode));
	}
	
	public void switchLevels(Level state) {
		Movement.stop();
		currentLevel = state;
		currentLevel.takeControl();
	}
}

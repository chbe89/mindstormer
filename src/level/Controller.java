package level;

import barcode.Barcode;
import movement.Movement;

import java.util.Map;
import java.util.HashMap;

public class Controller {
	private Map<Barcode, Level> levels;
	private Level initialState;
	private Level currentState;
	
	public Controller() {
		levels = new HashMap<>();
		levels.put(new Barcode(), new LevelFollowLine());
	}
	
	public void barcodeFound(Barcode barcode) {
		switchStates(levels.get(barcode));
	}
	
	public void switchStates(Level state) {
		Movement.stop();
		currentState = state;
		currentState.takeControl();
	}
}

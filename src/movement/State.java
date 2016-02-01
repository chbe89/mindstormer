package movement;

public final class State {
	
	private State() {};

	protected static Movement.Mode leftMode;
	protected static Movement.Mode rightMode;
	protected static float leftSpeed;
	protected static float rightSpeed;
	
	public static Movement.Mode getModeLeft() {
		return leftMode;
	}
	
	public static Movement.Mode getModeRight() {
		return rightMode;
	}
	
	public static float getSpeedLeft() {
		return leftSpeed;
	}
	
	public static float getSpeedRight() {
		return rightSpeed;
	}
	
}

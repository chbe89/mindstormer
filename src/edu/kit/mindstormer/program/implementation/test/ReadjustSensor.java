package edu.kit.mindstormer.program.implementation.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.utility.Delay;
import edu.kit.mindstormer.movement.Movement;
import edu.kit.mindstormer.movement.State;
import edu.kit.mindstormer.program.AbstractProgram;
import edu.kit.mindstormer.program.OperatingSystem;
import edu.kit.mindstormer.util.Listeners;
import edu.kit.mindstormer.util.SmartKey;
import edu.kit.mindstormer.util.TimePad;

public class ReadjustSensor extends AbstractProgram {

	private CountDownLatch latch = new CountDownLatch(1);
	private final AtomicBoolean move = new AtomicBoolean();
	private final SensorKeyListener listener = new SensorKeyListener(move, this);

	@Override
	public void initialize() {
		super.initialize();

		Button.UP.addKeyListener(listener);
		Button.DOWN.addKeyListener(listener);
		Button.ENTER.addKeyListener(listener);
	}

	@Override
	public void run() {
		nonBlockingWait();
	}

	private void nonBlockingWait() {
		while (true) {
			try {
				latch.await();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

	}

	@Override
	public void terminate() {
		super.terminate();

		Listeners.removeListener(Button.UP, listener);
		Listeners.removeListener(Button.DOWN, listener);
		Listeners.removeListener(Button.ENTER, listener);

		latch.countDown();
	}

	private void checkAndMove(int angle) {
		move.set(false);
		Delay.msDelay(15);
		if (move.compareAndSet(false, true)) {
			moveSensor(angle);
		}
	}

	private void moveSensor(int angle) {
		while (move.get()) {
			Movement.rotateSensorMotor(angle);
			State.waitForSensorMotor();
		}
	}

	private static class SensorKeyListener implements KeyListener {

		private final TimePad pad = new TimePad(75);
		private final AtomicBoolean move;
		private final ReadjustSensor readjustSensor;

		public SensorKeyListener(AtomicBoolean move, ReadjustSensor readjustSensor) {
			this.move = move;
			this.readjustSensor = readjustSensor;
		}

		@Override
		public void keyPressed(Key k) {
			// do nothing
		}

		@Override
		public void keyReleased(Key k) {
			if (!pad.requestTime())
				return;

			SmartKey key = SmartKey.from(k);
			switch (key) {
			case DOWN:
				OperatingSystem.displayText("Moving sensor down...");
				new Thread(new Runnable() {
					@Override
					public void run() {
						readjustSensor.checkAndMove(-1);
					}
				}).start();
				break;
			case UP:
				OperatingSystem.displayText("Moving sensor up...");
				new Thread(new Runnable() {
					@Override
					public void run() {
						readjustSensor.checkAndMove(1);
					}
				}).start();
				break;
			case ENTER:
				move.set(false);
				OperatingSystem.displayText("Sensor stopped moving.");
				break;
			default:
				break;
			}
		}
	}
}

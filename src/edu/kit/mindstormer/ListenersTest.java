package edu.kit.mindstormer;

import java.lang.reflect.Constructor;
import java.util.List;

import edu.kit.mindstormer.util.Listeners;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.internal.ev3.EV3Key;
import lejos.internal.ev3.EV3Keys;

@SuppressWarnings("restriction")
public class ListenersTest {

	public static void main(String[] args) throws Exception {
		System.out.println("Test Listeners");
		EV3Key key = createKey(null, "ButtonUp");
		System.out.println(key);

		try {
			key.addKeyListener(new KeyListener() {

				@Override
				public void keyReleased(Key k) {
					// TODO Auto-generated method stub

				}

				@Override
				public void keyPressed(Key k) {
					// TODO Auto-generated method stub

				}

				@Override
				public String toString() {
					return "TestKeyListener";

				}
			});
		} catch (NullPointerException e) {
			System.out.println(e);
		}

		List<KeyListener> listeners = Listeners.getListeners(key);
		System.out.println(listeners);

		Listeners.removeListeners(key);
		listeners = Listeners.getListeners(key);
		System.out.println(listeners);

	}

	private static EV3Key createKey(EV3Keys keys, String name) throws Exception {
		Class<EV3Key> clazz = EV3Key.class;
		Constructor<EV3Key> ctor = clazz.getDeclaredConstructor(EV3Keys.class,
				String.class);
		EV3Key key = ctor.newInstance(keys, name);
		return key;
	}
}

package edu.kit.mindstormer.util;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.internal.ev3.EV3Key;

@SuppressWarnings("restriction")
public final class Listeners {

	private Listeners() {
		// ctor
	}

	public static void removeListeners(Key key) {
		try {
			List<KeyListener> listeners = getListeners(key);
			if (!listeners.isEmpty())
				listeners.clear();
		} catch (Exception e) {
			// do nothing
		}
	}
	
	public static List<KeyListener> getListeners(Key key) throws Exception {
		EV3Key k = cast(key);
		if (k == null)
			return Collections.emptyList();

		Object obj = getMember(k, "listeners");

		if (obj == null)
			return Collections.emptyList();

		if (List.class.isInstance(obj)) {
			@SuppressWarnings("unchecked")
			List<KeyListener> listeners = (List<KeyListener>) List.class
					.cast(obj);
			return listeners;
		}

		return Collections.emptyList();
	}

	private static Object getMember(EV3Key k, String member)
			throws NoSuchFieldException, IllegalAccessException {
		Field field = EV3Key.class.getDeclaredField(member);
		field.setAccessible(true);
		Object obj = field.get(k);
		return obj;
	}

	private static EV3Key cast(Key key) {
		EV3Key k = null;
		if (EV3Key.class.isInstance(key))
			k = EV3Key.class.cast(key);
		return k;
	}
}

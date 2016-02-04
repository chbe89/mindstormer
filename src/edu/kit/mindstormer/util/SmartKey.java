package edu.kit.mindstormer.util;

import lejos.hardware.Button;
import lejos.hardware.Key;

public enum SmartKey {

	// @formatter:off
	ENTER(Button.ENTER), 
	ESCAPE(Button.ESCAPE), 
	UP(Button.UP),
	DOWN(Button.DOWN),
	RIGHT(Button.RIGHT),
	LEFT(Button.LEFT);
	// @formatter:on

	private final Key key;

	private SmartKey(Key key) {
		this.key = key;
	}

	public static SmartKey from(Key key) {
		SmartKey smartKey = null;

		for (SmartKey s : values())
			if (s.key.equals(key))
				smartKey = s;

		return smartKey;
	}
}

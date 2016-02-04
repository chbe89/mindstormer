package edu.kit.mindstormer.util;

import java.util.concurrent.atomic.AtomicBoolean;

public class TimePad {

	private final AtomicBoolean isLocked;
	private final int ms;

	private long time = 0;

	public TimePad() {
		this(200);
	}

	public TimePad(int milliseconds) {
		this.ms = milliseconds;
		this.isLocked = new AtomicBoolean(false);
		this.time = System.currentTimeMillis() - 2 * ms;
	}

	public boolean requestTime() {
		if (isLocked()) {
			return false;
		} else {
			time = System.currentTimeMillis();
			boolean acquireLock = isLocked.compareAndSet(false, true);
			return acquireLock;
		}
	}

	public boolean isLocked() {
		long current = System.currentTimeMillis();
		boolean hasEnoughTimeElapsed = current - time > ms;
		if (hasEnoughTimeElapsed) {
			boolean isUnlocked = !isLocked.get() || tryUnlock();
			return !isUnlocked;
		}

		return isLocked.get();
	}

	private boolean tryUnlock() {
		return isLocked.compareAndSet(true, false);
	}
}

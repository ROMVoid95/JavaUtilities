package net.rom.lib.async.threads;

import java.util.Objects;

import net.rom.lib.annotations.DocsNeeded;

@DocsNeeded
public class DeathTimer {
	private final Runnable onTimeout;
	private final long timeout;
	private boolean updated = false, armed = true;

	public DeathTimer(long timeout, Runnable onTimeout) {
		Objects.requireNonNull(onTimeout, "onTimeout");

		this.timeout = timeout;
		this.onTimeout = onTimeout;
		this.updated = true;

		Thread thread = new Thread(this::threadCode, "DeathTimer#" + Integer.toHexString(hashCode()) + " DThread");
		thread.setDaemon(true);
		thread.start();
	}

	public DeathTimer arm() {
		armed = true;
		return this;
	}

	public DeathTimer disarm() {
		armed = false;
		return this;
	}

	public DeathTimer explode() {
		synchronized (this) {
			notify();
		}
		return this;
	}

	public DeathTimer reset() {
		updated = true;
		return explode();
	}

	private void threadCode() {
		while (updated) {
			updated = false;
			try {
				synchronized (this) {
					wait(timeout);
				}
			} catch (InterruptedException ignored) {
			}
		}

		if (armed) onTimeout.run();
	}
}


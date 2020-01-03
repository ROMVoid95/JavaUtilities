package net.rom.lib.async;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import net.rom.lib.async.threads.ScheduledTaskProcessor;

public class RateLimiter {
	private static final ScheduledTaskProcessor TASK_PROCESSOR = new ScheduledTaskProcessor("RateLimiter");

	private final Map<String, AtomicInteger> idsRateLimited = new HashMap<>();
	private final int max, timeout;

	public RateLimiter(int max, int timeout) {
		this.max = max;
		this.timeout = timeout;
	}

	/**
	 *
	 * @param id The id to check for ratelimits
	 * @return false if the id is ratelimited
	 */
	public boolean process(String id) {
		AtomicInteger currentLimit = idsRateLimited.computeIfAbsent(id, k -> new AtomicInteger());
		if (currentLimit.get() >= max) return false;
		currentLimit.incrementAndGet();
		TASK_PROCESSOR.addTask(System.currentTimeMillis() + timeout, currentLimit::decrementAndGet);
		return true;
	}
}


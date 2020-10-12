package net.rom.utility.async;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import net.rom.utility.annotations.ExtensionClass;
import net.rom.utility.async.threads.CompletableThread;

@ExtensionClass
public class Async {
	public static Thread current() {
		return Thread.currentThread();
	}

	public static <T> Future<T> future(String task, Callable<T> callable) {
		return new CompletableThread<>(task, callable);
	}

	public static <T> Future<T> future(Callable<T> callable) {
		return new CompletableThread<>(callable);
	}

	public static void sleep(long milis) {
		try {
			Thread.sleep(milis);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void sleep(long time, TimeUnit unit) {
		sleep(unit.toMillis(time));
	}

	/**
	 * Start an Async single thread task every x seconds. Replacement for Timer.
	 *
	 * @param task         The name of the task to run
	 * @param scheduled    The runnable.
	 * @param everySeconds the amount of seconds the task will take to loop.
	 */
	@Deprecated
	public static void task(String task, Runnable scheduled, int everySeconds) {
		Executors.newSingleThreadScheduledExecutor(r -> new Thread(r, task)).scheduleAtFixedRate(
			scheduled, 0, everySeconds, TimeUnit.SECONDS);
	}

	/**
	 * Start an Async single thread task every x seconds. Replacement for Timer.
	 *
	 * @param task         The name of the task to run
	 * @param scheduled    The consumer that accepts the Executor as parameter.
	 * @param everySeconds the amount of seconds the task will take to loop.
	 */
	@Deprecated
	public static void task(String task, Consumer<ScheduledExecutorService> scheduled, int everySeconds) {
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(r -> new Thread(r, task));
		executor.scheduleAtFixedRate(() -> scheduled.accept(executor), 0, everySeconds, TimeUnit.SECONDS);
	}

	public static ScheduledExecutorService task(String task, Consumer<ScheduledExecutorService> scheduled, long everyTime, TimeUnit unit) {
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(r -> new Thread(r, task));
		executor.scheduleAtFixedRate(() -> scheduled.accept(executor), 0, everyTime, unit);
		return executor;
	}

	public static ScheduledExecutorService task(String task, Runnable scheduled, long everyTime, TimeUnit unit) {
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(r -> new Thread(r, task));
		executor.scheduleAtFixedRate(scheduled, 0, everyTime, unit);
		return executor;
	}

	public static ScheduledExecutorService task(Consumer<ScheduledExecutorService> scheduled, long everyTime, TimeUnit unit) {
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(() -> scheduled.accept(executor), 0, everyTime, unit);
		return executor;
	}

	public static ScheduledExecutorService task(Runnable scheduled, long everyTime, TimeUnit unit) {
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(scheduled, 0, everyTime, unit);
		return executor;
	}

	/**
	 * Creates a thread with a Runnable and immediately starts the Thread
	 *
	 * @param doAsync the Runnable that the thread will run
	 * @return the Thread that is now executing the Runnable
	 */
	public static Thread thread(Runnable doAsync) {
		Thread thread = new Thread(doAsync);
		thread.start();
		return thread;
	}

	@Deprecated
	public static Thread thread(int sleepMilis, Runnable doAfter) {
		return thread(sleepMilis, TimeUnit.MILLISECONDS, doAfter);
	}

	public static Thread thread(long time, TimeUnit unit, Runnable doAfter) {
		Objects.requireNonNull(doAfter);

		return thread(() -> {
			sleep(time, unit);
			doAfter.run();
		});
	}

	/**
	 *Use {@link thread(String name, long time, TimeUnit unit, Runnable doAfter)}
	 */
	@Deprecated
	public static Thread thread(String name, int sleepMilis, Runnable doAfter) {
		return thread(name, sleepMilis, TimeUnit.MILLISECONDS, doAfter);
	}

	public static Thread thread(String name, long time, TimeUnit unit, Runnable doAfter) {
		Objects.requireNonNull(doAfter);

		return thread(name, () -> {
			sleep(time, unit);
			doAfter.run();
		});
	}

	/**
	 * Creates a thread with a Runnable and immediately starts the Thread
	 *
	 * @param name    The thread name
	 * @param doAsync The Runnable that the thread will run
	 * @return the Thread that is now executing the Runnable
	 */
	public static Thread thread(String name, Runnable doAsync) {
		Objects.requireNonNull(doAsync);

		Thread thread = new Thread(doAsync, name);
		thread.start();
		return thread;
	}
}


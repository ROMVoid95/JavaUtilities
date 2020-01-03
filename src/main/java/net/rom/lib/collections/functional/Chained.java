package net.rom.lib.collections.functional;

import java.util.Objects;
import java.util.function.Function;

public class Chained<T> {
	private final T original;

	public Chained(T original) {
		this.original = original;
	}

	public <U> Chained<U> apply(Function<? super T, ? extends U> mapper) {
		Objects.requireNonNull(mapper);
		return new Chained<>(mapper.apply(original));
	}

	public <U> Chained<U> flatApply(Function<? super T, Chained<U>> mapper) {
		Objects.requireNonNull(mapper);
		return Objects.requireNonNull(mapper.apply(original));
	}

	public T get() {
		return original;
	}
}


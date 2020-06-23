package net.rom.lib.collections.thread;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public interface ThreadMap<V> extends Supplier<V>, Map<Thread, V> {
	V compute(BiFunction<? super Thread, ? super V, ? extends V> remappingFunction);

	V computeIfAbsent(Function<? super Thread, ? extends V> mappingFunction);

	V computeIfPresent(BiFunction<? super Thread, ? super V, ? extends V> remappingFunction);

	boolean containsKey();

	void copy(Thread parent, Thread child);

	V merge(V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction);

	V put(V value);

	V putIfAbsent(V value);

	V remove();

	boolean removeValue(V value);

	V replace(V value);

	boolean replaceValue(V oldValue, V newValue);

	void setChild(Thread parent);

	void setParent(Thread child);

	void clear();
}


/*
 * Decompiled with CFR 0.145.
 */
package net.rom.lib.collections.functional;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class ChainMapWrapper<K, V> implements ChainMap<K, V> {
	private final Map<K, V> map;

	public ChainMapWrapper(Map<K, V> map) {
		this.map = map;
	}

	@Override
	public Map<K, V> asMap() {
		return map;
	}

	@Override
	public ChainMap<K, V> asMap(Consumer<Map<K, V>> map) {
		map.accept(this.map);
		return this;
	}

	@Override
	public ChainMap<K, V> clear() {
		map.clear();
		return this;
	}

	@Override
	public ChainMap<K, V> containsKey(K key, Consumer<Boolean> contains) {
		contains.accept(map.containsKey(key));
		return this;
	}

	@Override
	public ChainMap<K, V> containsValue(V value, Consumer<Boolean> contains) {
		contains.accept(map.containsValue(value));
		return this;
	}

	@Override
	public ChainMap<K, V> entrySet(Consumer<Set<Entry<K, V>>> entrySet) {
		entrySet.accept(map.entrySet());
		return this;
	}

	@Override
	public ChainMap<K, V> get(K key, Consumer<V> value) {
		value.accept(map.get(key));
		return this;
	}

	@Override
	public ChainMap<K, V> isEmpty(Consumer<Boolean> empty) {
		empty.accept(map.isEmpty());
		return this;
	}

	@Override
	public ChainMap<K, V> keySet(Consumer<Set<K>> keySet) {
		keySet.accept(map.keySet());
		return this;
	}

	@Override
	public ChainMap<K, V> put(K key, V value, Consumer<V> oldValue) {
		oldValue.accept(map.put(key, value));
		return this;
	}

	@Override
	public ChainMap<K, V> putAll(Map<? extends K, ? extends V> m) {
		map.putAll(m);
		return this;
	}

	@Override
	public ChainMap<K, V> remove(K key, Consumer<V> oldValue) {
		oldValue.accept(map.remove(key));
		return this;
	}

	@Override
	public ChainMap<K, V> size(IntConsumer size) {
		size.accept(map.size());
		return this;
	}

	@Override
	public ChainMap<K, V> values(Consumer<Collection<V>> values) {
		values.accept(map.values());
		return this;
	}
}


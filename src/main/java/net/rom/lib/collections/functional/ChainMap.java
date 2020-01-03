package net.rom.lib.collections.functional;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.*;

import net.rom.lib.annotations.Incubating;

@Incubating
public interface ChainMap<K, V> {
	static <K, V> ChainMap<K, V> of(Map<K, V> map) {
		return new ChainMapWrapper<>(map);
	}

	Map<K, V> asMap();

	ChainMap<K, V> asMap(Consumer<Map<K, V>> map);

	ChainMap<K, V> clear();

	ChainMap<K, V> containsKey(K key, Consumer<Boolean> contains);

	ChainMap<K, V> containsValue(V value, Consumer<Boolean> contains);

	ChainMap<K, V> entrySet(Consumer<Set<Entry<K, V>>> entrySet);

	ChainMap<K, V> get(K key, Consumer<V> value);

	ChainMap<K, V> isEmpty(Consumer<Boolean> empty);

	ChainMap<K, V> keySet(Consumer<Set<K>> keySet);

	ChainMap<K, V> put(K key, V value, Consumer<V> oldValue);

	ChainMap<K, V> putAll(java.util.Map<? extends K, ? extends V> m);

	ChainMap<K, V> remove(K key, Consumer<V> oldValue);

	ChainMap<K, V> size(IntConsumer size);

	ChainMap<K, V> values(Consumer<Collection<V>> values);

	default ChainMap<K, V> compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return compute(key, remappingFunction, r -> {
		});
	}

	default ChainMap<K, V> compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction, Consumer<V> result) {
		Objects.requireNonNull(remappingFunction);
		Objects.requireNonNull(result);

		return get(key, oldValue -> {
			V newValue = remappingFunction.apply(key, oldValue);
			if (newValue == null) {
				if (oldValue != null || asMap().containsKey(key)) remove(key);
				result.accept(null);
			} else {
				put(key, newValue);
				result.accept(newValue);
			}
		});
	}

	default ChainMap<K, V> computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
		return computeIfAbsent(key, mappingFunction, r -> {
		});
	}

	default ChainMap<K, V> computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction, Consumer<V> result) {
		Objects.requireNonNull(mappingFunction);
		Objects.requireNonNull(result);

		return get(key, v -> {
			if (v == null) {
				V newValue;
				if ((newValue = mappingFunction.apply(key)) != null) put(key, newValue);
			}
		});
	}

	default ChainMap<K, V> computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return computeIfPresent(key, remappingFunction, r -> {
		});
	}

	default ChainMap<K, V> computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction, Consumer<V> result) {
		Objects.requireNonNull(remappingFunction);
		Objects.requireNonNull(result);

		return get(key, oldValue -> {
			if ((oldValue != null)) {
				V newValue = remappingFunction.apply(key, oldValue);
				if (newValue != null) {
					put(key, newValue);
					result.accept(newValue);
					return;
				}

				remove(key);
				result.accept(null);
				return;
			}

			result.accept(null);
		});
	}

	default ChainMap<K, V> forEach(BiConsumer<? super K, ? super V> action) {
		Objects.requireNonNull(action);
		return this.entrySet(entrySet -> {
			for (Entry<K, V> entry : entrySet) {
				K k;
				V v;
				try {
					k = entry.getKey();
					v = entry.getValue();
				} catch (IllegalStateException ise) {
					// this usually means the entry is no longer in the map.
					throw new ConcurrentModificationException(ise);
				}
				action.accept(k, v);
			}
		});
	}

	default ChainMap<K, V> getOrDefault(K key, V defaultValue, Consumer<V> value) {
		Objects.requireNonNull(value);
		return get(key, v -> {
			if (v != null) {
				value.accept(v);
				return;
			}

			containsKey(key, b -> value.accept(b ? null : defaultValue));
		});
	}

	default ChainMap<K, V> merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		return merge(key, value, remappingFunction, r -> {
		});
	}

	default ChainMap<K, V> merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction, Consumer<V> result) {
		Objects.requireNonNull(remappingFunction);
		Objects.requireNonNull(value);
		Objects.requireNonNull(result);

		return get(key, oldValue -> {
			V newValue = (oldValue == null) ? value : remappingFunction.apply(oldValue, value);

			if (newValue == null) remove(key);
			else put(key, newValue);

			result.accept(newValue);
		});
	}

	default ChainMap<K, V> put(K key, V value) {
		return put(key, value, r -> {
		});
	}

	default ChainMap<K, V> putIfAbsent(K key, V value) {
		return putIfAbsent(key, value, r -> {
		});
	}

	default ChainMap<K, V> putIfAbsent(K key, V value, Consumer<V> result) {
		Objects.requireNonNull(result);
		return get(key, v -> {
			if (v == null) {
				put(key, value, result);
			} else {
				result.accept(v);
			}
		});
	}

	default ChainMap<K, V> remove(K key) {
		return remove(key, r -> {
		});
	}

	default ChainMap<K, V> remove(K key, V value) {
		return remove(key, value, r -> {
		});
	}

	default ChainMap<K, V> remove(K key, V value, Consumer<Boolean> result) {
		Objects.requireNonNull(result);
		return get(key, curValue -> containsKey(key, containsKey -> {
			if (!Objects.equals(curValue, value) || (curValue == null && !containsKey)) {
				result.accept(false);
				return;
			}

			remove(key);
			result.accept(true);
		}));
	}

	default ChainMap<K, V> replace(K key, V oldValue, V newValue, Consumer<Boolean> result) {
		Objects.requireNonNull(result);
		return get(key, curValue -> containsKey(key, containsKey -> {
			if (!Objects.equals(curValue, oldValue) || (curValue == null && !containsKey)) {
				result.accept(false);
			}
			put(key, newValue);
			result.accept(true);
		}));
	}

	default ChainMap<K, V> replace(K key, V value, Consumer<V> result) {
		Objects.requireNonNull(result);
		return get(key, curValue -> containsKey(key, containsKey -> {
			if (curValue != null || containsKey) {
				put(key, value, result);
				return;
			}
			result.accept(null);
		}));

	}

	default ChainMap<K, V> replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
		Objects.requireNonNull(function);
		return entrySet(entrySet -> {
			for (Entry<K, V> entry : entrySet) {
				K k;
				V v;
				try {
					k = entry.getKey();
					v = entry.getValue();
				} catch (IllegalStateException ise) {
					// this usually means the entry is no longer in the map.
					throw new ConcurrentModificationException(ise);
				}

				// ise thrown from function is not a cme.
				v = function.apply(k, v);

				try {
					entry.setValue(v);
				} catch (IllegalStateException ise) {
					// this usually means the entry is no longer in the map.
					throw new ConcurrentModificationException(ise);
				}
			}
		});
	}
}
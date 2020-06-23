package net.rom.lib.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.rom.lib.annotations.ExtensionClass;
import net.rom.lib.collections.functional.ChainMap;
import net.rom.lib.collections.functional.ChainMapWrapper;

@ExtensionClass
public class CollectionUtils {
	public static <K, V> ChainMap<K, V> chainable(Map<K, V> map) {
		return new ChainMapWrapper<>(map);
	}

	@SafeVarargs
	public static <K, V> Map<K, V> concat(Map<? extends K, ? extends V>... maps) {
		return Arrays.stream(maps)
			.flatMap(map -> map.entrySet().stream())
			.collect(Collectors.toMap(
				Map.Entry::getKey,
				Map.Entry::getValue
			));
	}

	@SafeVarargs
	public static <K, V> Map<K, V> concat(BinaryOperator<V> resolver, Map<? extends K, ? extends V>... maps) {
		return Arrays.stream(maps)
			.flatMap(map -> map.entrySet().stream())
			.collect(Collectors.toMap(
				Map.Entry::getKey,
				Map.Entry::getValue,
				resolver
			));
	}

	@SafeVarargs
	public static <K, V> Map<K, V> concat(Supplier<Map<K, V>> mapSupplier, BinaryOperator<V> resolver, Map<? extends K, ? extends V>... maps) {
		return Arrays.stream(maps)
			.flatMap(map -> map.entrySet().stream())
			.collect(Collectors.toMap(
				Map.Entry::getKey,
				Map.Entry::getValue,
				resolver,
				mapSupplier
			));
	}

	public static Function<String, Iterable<String>> iterable(Pattern pattern) {
		return s -> iterable(pattern, s);
	}

	public static Iterable<String> iterable(Pattern pattern, String string) {
		return () -> {
			Matcher matcher = pattern.matcher(string);
			return new Iterator<String>() {
				@Override
				public boolean hasNext() {
					return matcher.find();
				}

				@Override
				public String next() {
					return matcher.group();
				}
			};
		};
	}

	public static <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
		return list.stream().map(mapper).collect(Collectors.toList());
	}

	public static <T> T random(List<T> list, Random random) {
		return list.get(random.nextInt(list.size()));
	}

	public static <T> T random(T[] array, Random random) {
		return array[random.nextInt(array.length)];
	}

	public static <T> T random(List<T> list) {
		return list.get(random(list.size()));
	}

	public static <T> T random(T[] array) {
		return array[random(array.length)];
	}

	public static int random(int size) {
		return (int) Math.floor(Math.random() * size);
	}

	@SuppressWarnings("unused")
	public static <T> Comparator<T> randomOrder() {
		ThreadLocalRandom r = ThreadLocalRandom.current();
		int x = r.nextInt(), y = r.nextInt();
		boolean b = r.nextBoolean();
		return Comparator.comparingInt((T t) -> t.hashCode() ^ x)
			.thenComparingInt(s -> s.toString().length() ^ y);
	}

	@SafeVarargs
	public static <T> Stream<T> stream(T... objs) {
		return Stream.of(objs);
	}

	public static <T> String toString(Collection<T> collection) {
		return toString(collection, Object::toString);
	}

	public static <T> String toString(Collection<T> collection, Function<T, String> toString) {
		return toString(collection, toString, "");
	}

	public static <T> String toString(Collection<T> collection, String join) {
		return toString(collection, Object::toString, join);
	}

	public static <T> String toString(Collection<T> collection, Function<T, String> toString, String join) {
		return collection.stream().map(toString).collect(Collectors.joining(join));
	}

	private static <T> Iterator<T> iterator(Enumeration<T> e) {
		return new Iterator<T>() {
			@Override
			public boolean hasNext() {
				return e.hasMoreElements();
			}

			@Override
			public T next() {
				return e.nextElement();
			}
		};
	}

	@SuppressWarnings("unused")
	private static <T> List<T> toList(Enumeration<T> e) {
		List<T> list = new ArrayList<>();
		iterator(e).forEachRemaining(list::add);
		return list;
	}
}


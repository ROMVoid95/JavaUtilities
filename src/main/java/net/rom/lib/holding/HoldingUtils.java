package net.rom.lib.holding;

import java.util.Iterator;
import java.util.function.Function;

import net.rom.lib.annotations.ExtensionClass;
import net.rom.lib.holding.objects.Holder;
import net.rom.lib.holding.objects.Indexed;
import net.rom.lib.holding.objects.Indexed.Indexer;
import net.rom.lib.holding.objects.Pointer;
import net.rom.lib.holding.objects.Switch;
@ExtensionClass
public class HoldingUtils {
	public static <T> Holder<T> hold(T obj) {
		return new Holder<>(obj);
	}

	public static <T> Function<T, Indexed<T>> index() {
		Indexer indexer = new Indexer();
		return indexer::index;
	}

	public static <T> Iterator<Indexed<T>> indexed(Iterator<T> iterator) {
		Indexer indexer = new Indexer();
		return new Iterator<Indexed<T>>() {
			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public Indexed<T> next() {
				return indexer.index(iterator.next());
			}
		};
	}

	public static <T> Iterable<Indexed<T>> indexed(Iterable<T> iterable) {
		return () -> indexed(iterable.iterator());
	}

	public static <T> Pointer<T> point(T obj) {
		return new Pointer<T>(obj);
	}

	public static Switch switchFor(boolean value) {
		return new Switch(value);
	}
}


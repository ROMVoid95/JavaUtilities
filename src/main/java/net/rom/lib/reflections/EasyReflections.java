package net.rom.lib.reflections;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class EasyReflections {
	public static <T, R> Function<T, R> getObjectField(Class<T> clazz, String fieldName) {
		Field field = Easyfier.field(clazz, fieldName);
		return t -> Easyfier.get(field, t);
	}

	public static <R> Supplier<R> getStaticField(Class<?> clazz, String fieldName) {
		Field field = Easyfier.field(clazz, fieldName);
		return () -> Easyfier.get(field, null);
	}

	public static <T, R> BiConsumer<T, R> setObjectField(Class<T> clazz, String fieldName) {
		Field field = Easyfier.field(clazz, fieldName);
		return (t, r) -> Easyfier.set(field, t, r);
	}

	public static <T, R> BiConsumer<T, R> setObjectFinalField(Class<T> clazz, String fieldName) {
		Field field = Easyfier.field(clazz, fieldName);
		return (t, r) -> Easyfier.set(field, t, r);
	}

	public static <R> Consumer<R> setStaticField(Class<?> clazz, String fieldName) {
		Field field = Easyfier.field(clazz, fieldName);
		return r -> Easyfier.set(field, null, r);
	}

	public static <R> Consumer<R> setStaticFinalField(Class<?> clazz, String fieldName) {
		Field field = Easyfier.field(clazz, fieldName);
		return r -> Easyfier.set(field, null, r);
	}
}

@SuppressWarnings("unchecked")
class Easyfier {
	static <T, R> MethodAccesser<T, R> callable(Method method) {
		return t -> args -> {
			try {
				return ((R) method.invoke(t, args));
			} catch (IllegalAccessException e) {
				throw new IllegalStateException(e);
			} catch (InvocationTargetException e) {
				throw new IllegalStateException(e.getCause());
			}
		};
	}

	static Field field(Class<?> c, String f) {
		try {
			return c.getDeclaredField(f);
		} catch (NoSuchFieldException e) {
			throw new IllegalArgumentException(e);
		}
	}

	static <R> R get(Field field, Object object) {
		try {
			field.setAccessible(true);
			return (R) field.get(object);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

	static void set(Field field, Object object, Object set) {
		try {
			field.setAccessible(true);
			field.set(object, set);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

	static void setFinal(Field field, Object object, Object set) {
		set(field(Field.class, "modifiers"), field, field.getModifiers() & ~Modifier.FINAL);
		set(field, object, set);
	}
}


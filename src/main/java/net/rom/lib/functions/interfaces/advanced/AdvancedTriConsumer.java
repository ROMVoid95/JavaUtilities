/*
 * Decompiled with CFR 0.145.
 */
package net.rom.lib.functions.interfaces.advanced;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import net.rom.lib.functions.interfaces.TriConsumer;

public interface AdvancedTriConsumer<T, U, V>
extends TriConsumer<T, U, V> {
    public static <T, U, V> AdvancedTriConsumer<T, U, V> of(TriConsumer<T, U, V> wrapped) {
        return (arg_0, arg_1, arg_2) -> wrapped.accept(arg_0, arg_1, arg_2);
    }

    @Override
    default public AdvancedTriConsumer<T, U, V> andThen(TriConsumer<? super T, ? super U, ? super V> after) {
        Objects.requireNonNull(after);
        return (t, u, v) -> {
            this.accept(t, u, v);
            after.accept(t, u, v);
        };
    }

    default public <W, X, Y> AdvancedTriConsumer<W, X, Y> compose(Function<? super W, ? extends T> before1, Function<? super X, ? extends U> before2, Function<? super Y, ? extends V> before3) {
        Objects.requireNonNull(before1);
        Objects.requireNonNull(before2);
        Objects.requireNonNull(before3);
        return (w, x, y) -> this.accept(before1.apply((W)w), before2.apply((X)x), before3.apply((Y)y));
    }

    default public <X, Y> AdvancedBiConsumer<X, Y> compose(Supplier<? extends T> before1, Function<? super X, ? extends U> before2, Function<? super Y, ? extends V> before3) {
        Objects.requireNonNull(before1);
        Objects.requireNonNull(before2);
        Objects.requireNonNull(before3);
        return (x, y) -> this.accept(before1.get(), before2.apply((X)x), before3.apply((Y)y));
    }

    default public <W, Y> AdvancedBiConsumer<W, Y> compose(Function<? super W, ? extends T> before1, Supplier<? extends U> before2, Function<? super Y, ? extends V> before3) {
        Objects.requireNonNull(before1);
        Objects.requireNonNull(before2);
        Objects.requireNonNull(before3);
        return (w, y) -> this.accept(before1.apply((W)w), before2.get(), before3.apply((Y)y));
    }

    default public <W, X> AdvancedBiConsumer<W, X> compose(Function<? super W, ? extends T> before1, Function<? super X, ? extends U> before2, Supplier<? extends V> before3) {
        Objects.requireNonNull(before1);
        Objects.requireNonNull(before2);
        Objects.requireNonNull(before3);
        return (w, x) -> this.accept(before1.apply((W)w), before2.apply((X)x), before3.get());
    }

    default public <Y> AdvancedConsumer<Y> compose(Supplier<? extends T> before1, Supplier<? extends U> before2, Function<? super Y, ? extends V> before3) {
        Objects.requireNonNull(before1);
        Objects.requireNonNull(before2);
        Objects.requireNonNull(before3);
        return y -> this.accept(before1.get(), before2.get(), before3.apply((Y)y));
    }

    default public <X> AdvancedConsumer<X> compose(Supplier<? extends T> before1, Function<? super X, ? extends U> before2, Supplier<? extends V> before3) {
        Objects.requireNonNull(before1);
        Objects.requireNonNull(before2);
        Objects.requireNonNull(before3);
        return x -> this.accept(before1.get(), before2.apply((X)x), before3.get());
    }

    default public <W> AdvancedConsumer<W> compose(Function<? super W, ? extends T> before1, Supplier<? extends U> before2, Supplier<? extends V> before3) {
        Objects.requireNonNull(before1);
        Objects.requireNonNull(before2);
        Objects.requireNonNull(before3);
        return w -> this.accept(before1.apply((W)w), before2.get(), before3.get());
    }

    default public AdvancedRunnable supply(Supplier<? extends T> supplier1, Supplier<? extends U> supplier2, Supplier<? extends V> supplier3) {
        Objects.requireNonNull(supplier1);
        Objects.requireNonNull(supplier2);
        Objects.requireNonNull(supplier3);
        return () -> this.accept(supplier1.get(), supplier2.get(), supplier3.get());
    }

    default public AdvancedBiConsumer<U, V> supplyFirst(Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier);
        return (u, v) -> this.accept(supplier.get(), u, v);
    }

    default public AdvancedBiConsumer<T, V> supplySecond(Supplier<? extends U> supplier) {
        Objects.requireNonNull(supplier);
        return (t, v) -> this.accept(t, supplier.get(), v);
    }

    default public AdvancedBiConsumer<T, U> supplyThird(Supplier<? extends V> supplier) {
        Objects.requireNonNull(supplier);
        return (t, u) -> this.accept(t, u, supplier.get());
    }
}


package net.rom.utility.functions.interfaces.advanced;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public interface AdvancedBiConsumer<T, U> extends BiConsumer<T, U> {
    public static <V, W> AdvancedBiConsumer<V, W> of(BiConsumer<V, W> wrapped) {
        return (arg_0, arg_1) -> wrapped.accept(arg_0, arg_1);
    }

    @Override
    default public AdvancedBiConsumer<T, U> andThen(BiConsumer<? super T, ? super U> after) {
        Objects.requireNonNull(after);
        return (l, r) -> {
            this.accept(l, r);
            after.accept(l, r);
        };
    }

    default public <V, W> AdvancedBiConsumer<V, W> compose(Function<? super V, ? extends T> before1, Function<? super W, ? extends U> before2) {
        Objects.requireNonNull(before1);
        Objects.requireNonNull(before2);
        return (v, w) -> this.accept(before1.apply((V)v), before2.apply((W)w));
    }

    default public <V> AdvancedConsumer<V> compose(Supplier<? extends T> before1, Function<? super V, ? extends U> before2) {
        Objects.requireNonNull(before1);
        Objects.requireNonNull(before2);
        return v -> this.accept(before1.get(), before2.apply((V)v));
    }

    default public <V> AdvancedConsumer<V> compose(Function<? super V, ? extends T> before1, Supplier<? extends U> before2) {
        Objects.requireNonNull(before1);
        Objects.requireNonNull(before2);
        return v -> this.accept(before1.apply((V)v), before2.get());
    }

    default public <V, W> AdvancedBiConsumer<V, W> compose(BiFunction<? super V, ? super W, ? extends T> before1, BiFunction<? super V, ? super W, ? extends U> before2) {
        Objects.requireNonNull(before1);
        Objects.requireNonNull(before2);
        return (v, w) -> this.accept(before1.apply((V)v, (W)w), before2.apply((V)v, (W)w));
    }

    default public AdvancedRunnable supply(Supplier<? extends T> supplier1, Supplier<? extends U> supplier2) {
        Objects.requireNonNull(supplier1);
        Objects.requireNonNull(supplier2);
        return () -> this.accept(supplier1.get(), supplier2.get());
    }

    default public AdvancedConsumer<U> supplyFirst(Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier);
        return u -> this.accept(supplier.get(), u);
    }

    default public AdvancedConsumer<T> supplySecond(Supplier<? extends U> supplier) {
        Objects.requireNonNull(supplier);
        return t -> this.accept(t, supplier.get());
    }
}


package net.rom.utility.functions.interfaces.advanced;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface AdvancedConsumer<T> extends Consumer<T> {
    public static <V> AdvancedConsumer<V> of(Consumer<V> wrapped) {
        return wrapped::accept;
    }

    @Override
    default public AdvancedConsumer<T> andThen(Consumer<? super T> after) {
        Objects.requireNonNull(after);
        return t -> {
            this.accept(t);
            after.accept(t);
        };
    }

    default public <V> AdvancedConsumer<V> compose(Function<? super V, ? extends T> before) {
        Objects.requireNonNull(before);
        return v -> this.accept(before.apply((V)v));
    }

    default public AdvancedRunnable supply(Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier);
        return () -> this.accept(supplier.get());
    }
}


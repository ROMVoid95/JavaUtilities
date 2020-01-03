/*
 * Decompiled with CFR 0.145.
 */
package net.rom.lib.functions.interfaces.advanced;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface AdvancedSupplier<T> extends Supplier<T> {
    public static <V> AdvancedSupplier<V> of(Supplier<V> wrapped) {
        return wrapped::get;
    }

    default public Optional<T> getOptionally() {
        return Optional.ofNullable(this.get());
    }

    default public <V> AdvancedSupplier<V> map(Function<T, V> map) {
        Objects.requireNonNull(map);
        return () -> map.apply(this.get());
    }

    default public Stream<T> stream() {
        return Stream.generate(this);
    }
}


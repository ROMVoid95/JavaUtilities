package net.rom.lib.functions.interfaces;

import java.util.Objects;

@FunctionalInterface
public interface TriConsumer<T, U, V> {
    public void accept(T var1, U var2, V var3);

    default public TriConsumer<T, U, V> andThen(TriConsumer<? super T, ? super U, ? super V> after) {
        Objects.requireNonNull(after);
        return (l, r, s) -> {
            this.accept(l, r, s);
            after.accept(l, r, s);
        };
    }
}


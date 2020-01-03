/*
 * Decompiled with CFR 0.145.
 */
package net.rom.lib.functions;


import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import net.rom.lib.functions.interfaces.TriConsumer;
import net.rom.lib.functions.interfaces.advanced.AdvancedBiConsumer;
import net.rom.lib.functions.interfaces.advanced.AdvancedConsumer;
import net.rom.lib.functions.interfaces.advanced.AdvancedRunnable;
import net.rom.lib.functions.interfaces.advanced.AdvancedSupplier;
import net.rom.lib.functions.interfaces.advanced.AdvancedTriConsumer;

public class FunctionUtils {
    public static AdvancedRunnable advanced(Runnable base) {
        return AdvancedRunnable.of(base);
    }

    public static <T> AdvancedConsumer<T> advanced(Consumer<T> base) {
        return AdvancedConsumer.of(base);
    }

    public static <T, U> AdvancedBiConsumer<T, U> advanced(BiConsumer<T, U> base) {
        return AdvancedBiConsumer.of(base);
    }

    public static <T, U, V> AdvancedTriConsumer<T, U, V> advanced(TriConsumer<T, U, V> base) {
        return AdvancedTriConsumer.of(base);
    }

    public static <T> AdvancedSupplier<T> advanced(Supplier<T> base) {
        return AdvancedSupplier.of(base);
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package net.rom.lib.functions.interfaces.advanced;

import java.util.Objects;

public interface AdvancedRunnable
extends Runnable {
    public static AdvancedRunnable of(Runnable wrapped) {
        return wrapped::run;
    }

    default public Runnable andThen(Runnable after) {
        Objects.requireNonNull(after);
        return () -> {
            this.run();
            after.run();
        };
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package net.rom.lib.collections.thread;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import net.rom.lib.async.Async;

public class DelegatedThreadMap<V> implements ThreadMap<V> {
    private final Map<Thread, V> delegated;

    public DelegatedThreadMap(Map<Thread, V> delegated) {
        this.delegated = delegated;
    }

    @Override
    public V compute(BiFunction<? super Thread, ? super V, ? extends V> remappingFunction) {
        return this.compute(Async.current(), remappingFunction);
    }

    @Override
    public V computeIfAbsent(Function<? super Thread, ? extends V> mappingFunction) {
        return this.computeIfAbsent(Async.current(), mappingFunction);
    }

    @Override
    public V computeIfPresent(BiFunction<? super Thread, ? super V, ? extends V> remappingFunction) {
        return this.computeIfPresent(Async.current(), remappingFunction);
    }

    @Override
    public boolean containsKey() {
        return this.containsKey(Async.current());
    }

    @Override
    public void copy(Thread parent, Thread child) {
        this.delegated.put(child, this.delegated.get(parent));
    }

    @Override
    public V merge(V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return this.merge(Async.current(), value, remappingFunction);
    }

    @Override
    public V put(V value) {
        return this.put(Async.current(), value);
    }

    @Override
    public V putIfAbsent(V value) {
        return this.putIfAbsent(Async.current(), value);
    }

    @Override
    public V remove() {
        return this.remove(Async.current());
    }

    @Override
    public boolean removeValue(V value) {
        return this.remove(Async.current(), value);
    }

    @Override
    public V replace(V value) {
        return this.replace(Async.current(), value);
    }

    @Override
    public boolean replaceValue(V oldValue, V newValue) {
        return this.replace(Async.current(), oldValue, newValue);
    }

    @Override
    public void setChild(Thread parent) {
        this.copy(parent, Async.current());
    }

    @Override
    public void setParent(Thread child) {
        this.copy(Async.current(), child);
    }

    @Override
    public void clear() {
        this.delegated.clear();
    }

    @Override
    public V get() {
        return this.delegated.get(Thread.currentThread());
    }

    @Override
    public int hashCode() {
        return this.delegated.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return this.delegated.equals(o);
    }

    @Override
    public int size() {
        return this.delegated.size();
    }

    @Override
    public boolean isEmpty() {
        return this.delegated.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.delegated.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.delegated.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return this.delegated.get(key);
    }

    @Override
    public V put(Thread key, V value) {
        return this.delegated.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return this.delegated.remove(key);
    }

    @Override
    public void putAll(Map<? extends Thread, ? extends V> m) {
        this.delegated.putAll(m);
    }

    @Override
    public Set<Thread> keySet() {
        return this.delegated.keySet();
    }

    @Override
    public Collection<V> values() {
        return this.delegated.values();
    }

    @Override
    public Set<Map.Entry<Thread, V>> entrySet() {
        return this.delegated.entrySet();
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return this.delegated.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super Thread, ? super V> action) {
        this.delegated.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super Thread, ? super V, ? extends V> function) {
        this.delegated.replaceAll(function);
    }

    @Override
    public V putIfAbsent(Thread key, V value) {
        return this.delegated.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return this.delegated.remove(key, value);
    }

    @Override
    public boolean replace(Thread key, V oldValue, V newValue) {
        return this.delegated.replace(key, oldValue, newValue);
    }

    @Override
    public V replace(Thread key, V value) {
        return this.delegated.replace(key, value);
    }

    @Override
    public V computeIfAbsent(Thread key, Function<? super Thread, ? extends V> mappingFunction) {
        return this.delegated.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public V computeIfPresent(Thread key, BiFunction<? super Thread, ? super V, ? extends V> remappingFunction) {
        return this.delegated.computeIfPresent(key, remappingFunction);
    }

    @Override
    public V compute(Thread key, BiFunction<? super Thread, ? super V, ? extends V> remappingFunction) {
        return this.delegated.compute(key, remappingFunction);
    }

    @Override
    public V merge(Thread key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return this.delegated.merge(key, (V)value, (BiFunction<? super V, ? super V, ? extends V>)remappingFunction);
    }
}


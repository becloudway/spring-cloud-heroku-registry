package com.xti.spring.cloud.heroku.discovery.metadata;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ObservableConcurrentHashMap<K,V> implements ConcurrentMap<K,V>, Serializable {

    private final ConcurrentHashMap<K,V> map;
    private MetadataProcedure procedure;

    public ObservableConcurrentHashMap(MetadataProcedure procedure) {
        this.map = new ConcurrentHashMap<>();
        this.procedure = procedure;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        V returnValue = map.put(key, value);
        procedure.invoke();
        return returnValue;
    }

    @Override
    public V remove(Object key) {
        V returnValue = map.remove(key);
        procedure.invoke();
        return returnValue;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
        procedure.invoke();
    }

    @Override
    public void clear() {
        map.clear();
        procedure.invoke();
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    @Override
    public V putIfAbsent(K key, V value) {
        V returnValue = map.putIfAbsent(key, value);
        procedure.invoke();
        return returnValue;
    }

    @Override
    public boolean remove(Object key, Object value) {
        boolean returnValue = map.remove(key, value);
        procedure.invoke();
        return returnValue;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        boolean returnValue = map.replace(key, oldValue, newValue);
        procedure.invoke();
        return returnValue;
    }

    @Override
    public V replace(K key, V value) {
        V returnValue = map.replace(key, value);
        procedure.invoke();
        return returnValue;
    }
}

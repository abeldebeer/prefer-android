package com.cookingfox.android.prefer.api.pref;

/**
 * Created by abeldebeer on 10/05/16.
 */
public interface PrefGroup<K extends Enum<K>> extends Iterable<Pref<K, ?>> {
    PrefGroup<K> add(Pref<K, ?> pref);

    Pref<K, ?> find(K key);

    <V> Pref<K, V> find(K key, Class<V> valueClass);

    Class<K> getKeyClass();
}

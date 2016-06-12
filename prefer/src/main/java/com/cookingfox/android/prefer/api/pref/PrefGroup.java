package com.cookingfox.android.prefer.api.pref;

import com.cookingfox.android.prefer.api.exception.IncorrectPrefKeyClassException;
import com.cookingfox.android.prefer.api.exception.PrefAlreadyAddedException;

/**
 * Represents a group of {@link Pref}.
 *
 * @param <K> References the concrete enum key class.
 */
public interface PrefGroup<K extends Enum<K>> extends Iterable<Pref<K, ?>> {

    /**
     * Add a Pref to this group.
     *
     * @param pref The Pref to add.
     * @return The current instance, for method chaining.
     * @throws PrefAlreadyAddedException      when a Pref with this key was already added.
     * @throws IncorrectPrefKeyClassException when the Pref has a key of a different enum class than
     *                                        this group.
     */
    PrefGroup<K> addPref(Pref<K, ?> pref);

    /**
     * Find a Pref by its unique key.
     *
     * @param key The enum key for the Pref.
     * @return The Pref if found, or `null` if not found.
     */
    Pref<K, ?> find(K key);

    /**
     * Find a Pref by its unique key and value type. This makes the result easier to work with than
     * {@link #find(Enum)}.
     *
     * @param key        The enum key for the Pref.
     * @param valueClass The value type of the Pref.
     * @param <V>        Indicates the concrete Pref value type.
     * @return The Pref if found, or `null` if not found.
     */
    <V> Pref<K, V> find(K key, Class<V> valueClass);

    /**
     * The enum key class for the Prefs in this group.
     *
     * @return The enum key class.
     */
    Class<K> getKeyClass();

}

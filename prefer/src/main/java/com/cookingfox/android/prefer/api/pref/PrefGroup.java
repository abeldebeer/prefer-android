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
     * Adds a listener for when the value changes of one of this group's added Prefs.
     *
     * @param listener The listener to add.
     */
    void addGroupValueChangedListener(OnGroupValueChanged<K> listener);

    /**
     * Add a Pref to this group.
     *
     * @param pref The Pref to add.
     * @throws PrefAlreadyAddedException      when a Pref with this key was already added.
     * @throws IncorrectPrefKeyClassException when the Pref has a key of a different enum class than
     *                                        this group.
     */
    void addPref(Pref<K, ?> pref);

    /**
     * Find a Pref by its unique key.
     *
     * @param key The enum key for the Pref.
     * @return The Pref if found, or `null` if not found.
     */
    Pref<K, ?> findPref(K key);

    /**
     * Find a Pref by its unique key and value type. This makes the result easier to work with than
     * {@link #findPref(Enum)}.
     *
     * @param key        The enum key for the Pref.
     * @param valueClass The value type of the Pref.
     * @param <V>        Indicates the concrete Pref value type.
     * @return The Pref if found, or `null` if not found.
     */
    <V> Pref<K, V> findPref(K key, Class<V> valueClass);

    /**
     * The enum key class for the Prefs in this group.
     *
     * @return The enum key class.
     */
    Class<K> getKeyClass();

    /**
     * Removes an added listener.
     *
     * @param listener The listener to remove.
     * @see #addGroupValueChangedListener(OnGroupValueChanged)
     */
    void removeGroupValueChangedListener(OnGroupValueChanged<K> listener);

}

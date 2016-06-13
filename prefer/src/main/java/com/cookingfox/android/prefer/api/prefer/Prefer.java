package com.cookingfox.android.prefer.api.prefer;

import com.cookingfox.android.prefer.api.pref.Pref;
import com.cookingfox.android.prefer.api.pref.PrefGroup;
import com.cookingfox.android.prefer.api.pref.PrefListener;
import com.cookingfox.android.prefer.api.prefer.typed.BooleanPrefer;
import com.cookingfox.android.prefer.api.prefer.typed.IntegerPrefer;
import com.cookingfox.android.prefer.api.prefer.typed.LongPrefer;
import com.cookingfox.android.prefer.api.prefer.typed.StringPrefer;

import java.util.Set;

/**
 * Manages groups of Prefs, by providing hooks to retrieve, set and observe the Pref values.
 */
public interface Prefer extends
        BooleanPrefer,
        IntegerPrefer,
        LongPrefer,
        StringPrefer {

    /**
     * Initialize the Prefer library.
     */
    void initializePrefer();

    /**
     * Dispose the Prefer library. Clears all added groups and listeners.
     */
    void disposePrefer();

    /**
     * Add a Pref group to manage.
     *
     * @param group The group to manage.
     * @param <K>   References the enum class for the Pref's key.
     */
    <K extends Enum<K>> void addGroup(PrefGroup<K> group);

    /**
     * Find a Pref group by its enum key class.
     *
     * @param keyClass The group's enum key class.
     * @param <K>      References the enum class for the Pref's key.
     * @return The group if found, or `null` if not found.
     */
    <K extends Enum<K>> PrefGroup<K> findGroup(Class<K> keyClass);

    /**
     * Returns the currently added Pref groups.
     *
     * @return The currently added Pref groups.
     */
    Set<PrefGroup<? extends Enum>> getGroups();

    /**
     * Registers a listener for when the value of the provided Pref changes.
     *
     * @param pref     The Pref to add a listener for.
     * @param listener The listener to add.
     * @param <K>      References the enum class for the Pref's key.
     * @param <V>      Indicates the Pref's value type.
     * @see Pref#addListener(PrefListener)
     */
    <K extends Enum<K>, V> void addListener(Pref<K, V> pref, PrefListener<V> listener);

    /**
     * Removes a Pref listener.
     *
     * @param pref     The Pref to remove the listener for.
     * @param listener The listener to remove.
     * @param <K>      References the enum class for the Pref's key.
     * @param <V>      Indicates the Pref's value type.
     * @see Pref#removeListener(PrefListener)
     */
    <K extends Enum<K>, V> void removeListener(Pref<K, V> pref, PrefListener<V> listener);

}

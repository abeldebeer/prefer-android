package com.cookingfox.android.prefer.api.prefer;

import com.cookingfox.android.prefer.api.pref.OnValueChanged;
import com.cookingfox.android.prefer.api.pref.Pref;
import com.cookingfox.android.prefer.api.pref.PrefGroup;
import com.cookingfox.android.prefer.impl.prefer.PreferHelper;

import java.util.Set;

/**
 * Manages groups of Prefs, by providing hooks to retrieve, set and observe the Pref values.
 */
public interface Prefer extends PreferHelper {

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
     * Adds a listener which is notified when the value of this preference changes.
     *
     * @param pref     The Pref to add a listener for.
     * @param listener The listener to add.
     * @param <K>      References the enum class for the Pref's key.
     * @param <V>      Indicates the Pref's value type.
     * @see Pref#addValueChangedListener(OnValueChanged)
     */
    <K extends Enum<K>, V> void addValueChangedListener(Pref<K, V> pref, OnValueChanged<V> listener);

    /**
     * Stops notifying this listener when this preference's value changes.
     *
     * @param pref     The Pref to remove the listener for.
     * @param listener The listener to remove.
     * @param <K>      References the enum class for the Pref's key.
     * @param <V>      Indicates the Pref's value type.
     * @see Pref#removeValueChangedListener(OnValueChanged)
     */
    <K extends Enum<K>, V> void removeValueChangedListener(Pref<K, V> pref, OnValueChanged<V> listener);

}

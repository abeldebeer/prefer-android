package com.cookingfox.android.prefer.api.pref;

/**
 * Wraps a Pref group. Extend this interface so you can provide access to the separate Pref objects
 * and the group they are contained in.
 *
 * @param <K> References the concrete enum key class.
 */
public interface Prefs<K extends Enum<K>> {

    /**
     * Returns Pref group that is associated with {@link K}.
     *
     * @return The Pref group.
     */
    PrefGroup<K> getPrefGroup();

}

package com.cookingfox.android.prefer.api.pref;

/**
 * Listener interface for when a {@link Pref}'s value changes.
 *
 * @param <V> Indicates the Pref's value type.
 */
public interface PrefListener<V> {

    /**
     * The Pref's value has changed.
     *
     * @param value The new value.
     */
    void onValueChanged(V value);

}

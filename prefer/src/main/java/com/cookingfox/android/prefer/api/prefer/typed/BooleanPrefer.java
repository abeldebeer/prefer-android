package com.cookingfox.android.prefer.api.prefer.typed;

import com.cookingfox.android.prefer.api.prefer.Prefer;

/**
 * Prefer functionality for boolean typed preferences.
 */
public interface BooleanPrefer {

    /**
     * Returns the value of the preference with the provided key, or returns the default value if it
     * is not found.
     *
     * @param key          The unique preference enum key.
     * @param defaultValue The default value to return if no preference for this key exists.
     * @return The preference value or the default value.
     */
    boolean getBoolean(Enum key, boolean defaultValue);

    /**
     * Sets the new value of the preference with the provided key.
     *
     * @param key   The unique preference enum key.
     * @param value The new value to set.
     */
    void putBoolean(Enum key, boolean value);

}

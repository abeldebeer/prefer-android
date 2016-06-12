package com.cookingfox.android.prefer.api.prefer.typed;

import com.cookingfox.android.prefer.api.prefer.Prefer;

/**
 * Prefer functionality for String typed preferences.
 */
public interface StringPrefer {

    /**
     * Returns the value of the preference with the provided key, or returns the default value if it
     * is not found.
     *
     * @param key          The unique preference enum key.
     * @param defaultValue The default value to return if no preference for this key exists.
     * @return The preference value or the default value.
     */
    String getString(Enum key, String defaultValue);

    /**
     * Sets the new value of the preference with the provided key.
     *
     * @param key   The unique preference enum key.
     * @param value The new value to set.
     * @return The current instance, for method chaining.
     */
    Prefer putString(Enum key, String value);

}

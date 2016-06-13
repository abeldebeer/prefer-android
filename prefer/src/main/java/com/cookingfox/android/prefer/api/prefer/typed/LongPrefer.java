package com.cookingfox.android.prefer.api.prefer.typed;

/**
 * Prefer functionality for long typed preferences.
 */
public interface LongPrefer {

    /**
     * Returns the value of the preference with the provided key, or returns the default value if it
     * is not found.
     *
     * @param key          The unique preference enum key.
     * @param defaultValue The default value to return if no preference for this key exists.
     * @return The preference value or the default value.
     */
    long getLong(Enum key, long defaultValue);

    /**
     * Sets the new value of the preference with the provided key.
     *
     * @param key   The unique preference enum key.
     * @param value The new value to set.
     */
    void putLong(Enum key, long value);

}

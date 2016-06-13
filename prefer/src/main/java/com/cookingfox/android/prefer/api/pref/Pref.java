package com.cookingfox.android.prefer.api.pref;

import com.cookingfox.android.prefer.api.exception.InvalidPrefValueException;

/**
 * Represents one preference object, for example "X is enabled" or "user name for Y". The key of a
 * Pref is an enum value, where the enum class represents the group of preferences.
 *
 * @param <K> References the enum class for this Pref's key.
 * @param <V> Indicates this Pref's value type.
 */
public interface Pref<K extends Enum<K>, V> extends PrefValidator<V> {

    /**
     * Registers a listener for when the value of this preference changes.
     *
     * @param listener The listener to add.
     * @return The current instance, for method chaining.
     */
    Pref<K, V> addListener(PrefListener<V> listener);

    /**
     * Returns this Pref's default value.
     *
     * @return The default value.
     */
    V getDefaultValue();

    /**
     * Returns this Pref's unique key.
     *
     * @return The unique key.
     */
    K getKey();

    /**
     * Returns this Pref's current value.
     *
     * @return The current value.
     */
    V getValue();

    /**
     * Removes the Pref listener.
     *
     * @param listener The listener to remove.
     * @return The current instance, for method chaining.
     */
    Pref<K, V> removeListener(PrefListener<V> listener);

    /**
     * Sets a custom validator implementation for this Pref.
     *
     * @param validator The validator to set.
     * @return The current instance, for method chaining.
     */
    Pref<K, V> setValidator(PrefValidator<V> validator);

    /**
     * Set a new value. Calls this Pref's validator if it is set.
     *
     * @param value The new value to set.
     * @return The current instance, for method chaining.
     * @throws InvalidPrefValueException when `value` is considered invalid by the validator.
     * @see #setValidator(PrefValidator)
     */
    Pref<K, V> setValue(V value) throws InvalidPrefValueException;

}

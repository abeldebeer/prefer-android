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
     * Sets a custom validator implementation for this Pref.
     *
     * @param validator The validator to set.
     */
    void setValidator(PrefValidator<V> validator);

    /**
     * Set a new value. Calls this Pref's validator if it is set.
     *
     * @param value The new value to set.
     * @throws InvalidPrefValueException when `value` is considered invalid by the validator.
     * @see #setValidator(PrefValidator)
     */
    void setValue(V value) throws InvalidPrefValueException;

    /**
     * Registers a subscriber for when the value of this preference changes.
     *
     * @param subscriber The subscriber to add.
     */
    void subscribe(OnValueChanged<V> subscriber);

    /**
     * Removes the Pref subscriber.
     *
     * @param subscriber The subscriber to remove.
     */
    void unsubscribe(OnValueChanged<V> subscriber);

}

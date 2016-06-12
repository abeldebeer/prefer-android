package com.cookingfox.android.prefer.api.pref;

/**
 * Validates a new value for a {@link Pref}. This allows Prefs to customize their validation.
 *
 * @param <V> Indicates the Pref's value type.
 */
public interface PrefValidator<V> {

    /**
     * Validate the Pref value. To provide an 'invalid' reason, throw an exception with the desired
     * message.
     *
     * @param value The value to check.
     * @return True when the value is valid, false if it is not. To provide an 'invalid' reason,
     * throw an exception with the desired message.
     * @throws Exception when the validation errors or when a validation reason needs to be
     *                   supplied.
     */
    boolean validate(V value) throws Exception;

}

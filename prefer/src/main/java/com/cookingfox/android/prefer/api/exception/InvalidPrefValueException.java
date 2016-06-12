package com.cookingfox.android.prefer.api.exception;

/**
 * Thrown when an invalid pref value is set.
 */
public class InvalidPrefValueException extends PreferException {

    public InvalidPrefValueException(String message) {
        super(message);
    }

    public InvalidPrefValueException(String message, Throwable cause) {
        super(message, cause);
    }

}

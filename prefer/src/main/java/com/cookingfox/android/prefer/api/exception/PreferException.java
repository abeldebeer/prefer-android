package com.cookingfox.android.prefer.api.exception;

/**
 * Base exception class in Prefer library.
 */
public class PreferException extends RuntimeException {

    public PreferException(String message) {
        super(message);
    }

    public PreferException(String message, Throwable cause) {
        super(message, cause);
    }

    public PreferException(Throwable cause) {
        super(cause);
    }

}

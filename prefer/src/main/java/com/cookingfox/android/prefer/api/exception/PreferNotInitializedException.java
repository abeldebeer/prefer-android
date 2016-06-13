package com.cookingfox.android.prefer.api.exception;

import com.cookingfox.android.prefer.api.prefer.Prefer;

/**
 * Thrown when an operation is performed while Prefer is not initialized.
 *
 * @see Prefer#initializePrefer()
 * @see Prefer#disposePrefer()
 */
public class PreferNotInitializedException extends PreferException {

    /**
     * @param detailMessage Explain which operation could not be performed.
     */
    public PreferNotInitializedException(String detailMessage) {
        super(String.format("%s - make sure you call `Prefer#initializePrefer()` first", detailMessage));
    }

}

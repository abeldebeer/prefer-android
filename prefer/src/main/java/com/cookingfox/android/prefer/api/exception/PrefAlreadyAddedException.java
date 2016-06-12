package com.cookingfox.android.prefer.api.exception;

import com.cookingfox.android.prefer.api.pref.Pref;

/**
 * Thrown when a Pref was already added to a PrefGroup.
 */
public class PrefAlreadyAddedException extends PreferException {

    public PrefAlreadyAddedException(Pref pref) {
        super(String.format("Pref with key '%s' was already added", pref.getKey()));
    }

}

package com.cookingfox.android.prefer.api.exception;

import com.cookingfox.android.prefer.api.pref.Pref;
import com.cookingfox.android.prefer.api.pref.PrefGroup;

/**
 * Thrown when a Pref is added to a group with a different key class.
 */
public class IncorrectPrefKeyClassException extends PreferException {

    public IncorrectPrefKeyClassException(Pref pref, PrefGroup group) {
        super(String.format("Pref with key '%s' is of different key class than group for '%s'",
                pref.getKey().getClass().getName() + '.' + pref.getKey(),
                group.getKeyClass().getName()));
    }

}

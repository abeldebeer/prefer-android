package com.cookingfox.android.prefer.api.exception;

import com.cookingfox.android.prefer.api.pref.PrefGroup;

/**
 * Thrown when a group was already added.
 */
public class GroupAlreadyAddedException extends PreferException {

    public GroupAlreadyAddedException(PrefGroup<?> group) {
        super(String.format("Group for '%s' was already added", group.getKeyClass()));
    }
}

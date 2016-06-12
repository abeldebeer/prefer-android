package com.cookingfox.android.prefer.api.pref.typed;

import com.cookingfox.android.prefer.api.pref.Pref;

/**
 * {@link Pref} implementation with a boolean value.
 */
public interface BooleanPref<K extends Enum<K>> extends Pref<K, Boolean> {
}

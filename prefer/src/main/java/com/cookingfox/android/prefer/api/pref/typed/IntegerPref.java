package com.cookingfox.android.prefer.api.pref.typed;

import com.cookingfox.android.prefer.api.pref.Pref;

/**
 * {@link Pref} implementation with an integer value.
 */
public interface IntegerPref<K extends Enum<K>> extends Pref<K, Integer> {
}

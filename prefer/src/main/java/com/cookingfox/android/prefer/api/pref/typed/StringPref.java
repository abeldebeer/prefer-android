package com.cookingfox.android.prefer.api.pref.typed;

import com.cookingfox.android.prefer.api.pref.Pref;

/**
 * {@link Pref} implementation with a String value.
 */
public interface StringPref<K extends Enum<K>> extends Pref<K, String> {
}

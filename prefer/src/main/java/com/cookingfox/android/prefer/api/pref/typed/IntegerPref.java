package com.cookingfox.android.prefer.api.pref.typed;

import com.cookingfox.android.prefer.api.pref.Pref;

/**
 * {@link Pref} implementation with an integer value.
 *
 * @param <K> References the enum class for this Pref's key.
 */
public interface IntegerPref<K extends Enum<K>> extends Pref<K, Integer> {
}

package com.cookingfox.android.prefer.api.pref.typed;

import com.cookingfox.android.prefer.api.pref.Pref;

/**
 * {@link Pref} implementation with a boolean value.
 *
 * @param <K> References the enum class for this Pref's key.
 */
public interface BooleanPref<K extends Enum<K>> extends Pref<K, Boolean> {
}

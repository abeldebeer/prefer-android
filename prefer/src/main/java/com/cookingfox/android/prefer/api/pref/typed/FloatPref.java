package com.cookingfox.android.prefer.api.pref.typed;

import com.cookingfox.android.prefer.api.pref.Pref;

/**
 * {@link Pref} implementation with a float value.
 *
 * @param <K> References the enum class for this Pref's key.
 */
public interface FloatPref<K extends Enum<K>> extends Pref<K, Float> {
}

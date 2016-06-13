package com.cookingfox.android.prefer.api.pref.typed;

import com.cookingfox.android.prefer.api.pref.Pref;

/**
 * {@link Pref} implementation with a long value.
 *
 * @param <K> References the enum class for this Pref's key.
 */
public interface LongPref<K extends Enum<K>> extends Pref<K, Long> {
}

package com.cookingfox.android.prefer_rx.api.pref.typed;

import com.cookingfox.android.prefer.api.pref.Pref;
import com.cookingfox.android.prefer.api.pref.typed.BooleanPref;
import com.cookingfox.android.prefer_rx.api.pref.RxPref;

/**
 * Rx observable {@link Pref} implementation with a boolean value.
 *
 * @param <K> References the enum class for this Pref's key.
 */
public interface BooleanRxPref<K extends Enum<K>> extends BooleanPref<K>, RxPref<K, Boolean> {
}

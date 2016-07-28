package com.cookingfox.android.prefer_rx.api.pref;

import com.cookingfox.android.prefer.api.pref.Pref;

import rx.Observable;

/**
 * Extends a {@link Pref} with Rx observable functionality.
 *
 * @param <K> References the enum class for this Pref's key.
 * @param <V> Indicates this Pref's value type.
 */
public interface RxPref<K extends Enum<K>, V> extends Pref<K, V> {

    /**
     * Returns an Rx observable for value changes of this Pref.
     *
     * @return Rx observable for value changes of this Pref.
     */
    Observable<V> observeValueChanges();

}

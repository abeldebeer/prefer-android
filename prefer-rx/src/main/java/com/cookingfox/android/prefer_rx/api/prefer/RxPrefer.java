package com.cookingfox.android.prefer_rx.api.prefer;

import com.cookingfox.android.prefer.api.exception.PreferNotInitializedException;
import com.cookingfox.android.prefer.api.pref.OnGroupValueChanged;
import com.cookingfox.android.prefer.api.pref.Pref;
import com.cookingfox.android.prefer.api.pref.PrefGroup;
import com.cookingfox.android.prefer.api.prefer.Prefer;

import rx.Observable;

/**
 * Extends {@link Prefer} with Rx observable functionality.
 */
public interface RxPrefer extends Prefer {

    /**
     * Creates a new observable for the provided group's Pref values.
     *
     * @param group The group for which to observe the pref value changes.
     * @param <K>   References the enum class for the Pref's key.
     * @return The Pref group observable.
     * @throws PreferNotInitializedException when Prefer has not been initialized yet.
     * @see #addGroupValueChangedListener(PrefGroup, OnGroupValueChanged)
     */
    <K extends Enum<K>> Observable<Pref<K, ?>> observeGroupValueChanges(PrefGroup<K> group);

    /**
     * Creates a new observable for the provided Pref's value.
     *
     * @param pref The Pref to observe for value changes.
     * @param <K>  References the enum class for the Pref's key.
     * @param <V>  Indicates the Pref's value type.
     * @return The Pref value observable.
     * @throws PreferNotInitializedException when Prefer has not been initialized yet.
     * @see #initializePrefer()
     */
    <K extends Enum<K>, V> Observable<V> observeValueChanges(Pref<K, V> pref);

}

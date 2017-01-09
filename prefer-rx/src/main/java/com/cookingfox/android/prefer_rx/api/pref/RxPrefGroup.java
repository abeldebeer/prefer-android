package com.cookingfox.android.prefer_rx.api.pref;

import com.cookingfox.android.prefer.api.exception.PreferNotInitializedException;
import com.cookingfox.android.prefer.api.pref.OnGroupValueChanged;
import com.cookingfox.android.prefer.api.pref.Pref;
import com.cookingfox.android.prefer.api.pref.PrefGroup;

import rx.Observable;

/**
 * Extends {@link PrefGroup} with Rx observable functionality.
 *
 * @param <K> References the concrete enum key class.
 */
public interface RxPrefGroup<K extends Enum<K>> extends PrefGroup<K> {

    /**
     * Creates a new observable for the provided the group's Prefs.
     *
     * @return The group value observable.
     * @throws PreferNotInitializedException when Prefer has not been initialized yet.
     * @see #addGroupValueChangedListener(OnGroupValueChanged)
     */
    Observable<Pref<K, ?>> observeGroupValueChanges();

}

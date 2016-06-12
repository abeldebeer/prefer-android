package com.cookingfox.android.prefer.api.pref;

import com.cookingfox.android.prefer.api.exception.InvalidPrefValueException;

/**
 * Created by abeldebeer on 10/05/16.
 */
public interface Pref<K extends Enum<K>, V> extends PrefValidator<V> {
    Pref<K, V> addListener(PrefListener<V> listener);

    V getDefaultValue();

    K getKey();

    V getValue();

//    Observable<V> observeValueChanges();

    Pref<K, V> removeListener(PrefListener<V> listener);

    Pref<K, V> setValidator(PrefValidator<V> validator);

    Pref<K, V> setValue(V value) throws InvalidPrefValueException;
}

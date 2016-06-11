package com.cookingfox.android.prefer.api.pref;

/**
 * Created by abeldebeer on 10/05/16.
 */
public interface PrefListener<V> {
    void onValueChanged(V value);
}

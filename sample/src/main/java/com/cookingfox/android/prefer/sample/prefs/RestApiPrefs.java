package com.cookingfox.android.prefer.sample.prefs;

import com.cookingfox.android.prefer_rx.api.pref.RxPrefs;
import com.cookingfox.android.prefer_rx.api.pref.typed.BooleanRxPref;
import com.cookingfox.android.prefer_rx.api.pref.typed.LongRxPref;
import com.cookingfox.android.prefer_rx.api.pref.typed.StringRxPref;

/**
 * Example REST API preferences.
 */
public interface RestApiPrefs extends RxPrefs<RestApiPrefs.Key> {

    enum Key {
        AuthToken,
        CacheEnabled,
        UpdateIntervalSeconds
    }

    StringRxPref<Key> authToken();

    BooleanRxPref<Key> cacheEnabled();

    LongRxPref<Key> updateIntervalSeconds();

}

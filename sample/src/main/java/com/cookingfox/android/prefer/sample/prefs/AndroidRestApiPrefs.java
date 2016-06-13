package com.cookingfox.android.prefer.sample.prefs;

import com.cookingfox.android.prefer.api.pref.PrefGroup;
import com.cookingfox.android.prefer_rx.api.pref.typed.BooleanRxPref;
import com.cookingfox.android.prefer_rx.api.pref.typed.IntegerRxPref;
import com.cookingfox.android.prefer_rx.api.pref.typed.StringRxPref;
import com.cookingfox.android.prefer_rx.impl.pref.AndroidRxPrefGroup;
import com.cookingfox.android.prefer_rx.impl.pref.typed.AndroidBooleanRxPref;
import com.cookingfox.android.prefer_rx.impl.pref.typed.AndroidIntegerRxPref;
import com.cookingfox.android.prefer_rx.impl.pref.typed.AndroidStringRxPref;
import com.cookingfox.android.prefer_rx.impl.prefer.SharedPreferencesRxPrefer;

/**
 * Implementation of {@link RestApiPrefs}.
 */
public class AndroidRestApiPrefs implements RestApiPrefs {

    private final AndroidRxPrefGroup<Key> group;
    private final AndroidStringRxPref<Key> authToken;
    private final AndroidBooleanRxPref<Key> cacheEnabled;
    private final AndroidIntegerRxPref<Key> updateIntervalSeconds;

    public AndroidRestApiPrefs(SharedPreferencesRxPrefer prefer) {
        group = prefer.addNewGroup(Key.class);
        group.setTitle("REST API");
        group.setSummary("REST API preferences.");

        authToken = group.addNewString(Key.AuthToken, "");
        authToken.setTitle("Authentication token");
        authToken.setSummary("Token for the REST API.");

        cacheEnabled = group.addNewBoolean(Key.CacheEnabled, true);
        cacheEnabled.setTitle("Cache enabled");
        cacheEnabled.setSummary("Whether API results should be cached.");

        updateIntervalSeconds = group.addNewInteger(Key.UpdateIntervalSeconds, 60);
        updateIntervalSeconds.setTitle("Update interval (seconds)");
        updateIntervalSeconds.setSummary("How often to fetch new data from the API.");
    }

    @Override
    public StringRxPref<Key> authToken() {
        return authToken;
    }

    @Override
    public BooleanRxPref<Key> cacheEnabled() {
        return cacheEnabled;
    }

    @Override
    public IntegerRxPref<Key> updateIntervalSeconds() {
        return updateIntervalSeconds;
    }

    @Override
    public PrefGroup<Key> getPrefGroup() {
        return group;
    }

}

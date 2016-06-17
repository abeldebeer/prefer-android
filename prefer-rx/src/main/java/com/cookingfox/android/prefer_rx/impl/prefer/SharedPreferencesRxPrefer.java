package com.cookingfox.android.prefer_rx.impl.prefer;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

import com.cookingfox.android.prefer.impl.prefer.PreferHelper;
import com.cookingfox.android.prefer.impl.prefer.SharedPreferencesHelper;
import com.cookingfox.android.prefer_rx.api.prefer.RxPrefer;

import static com.cookingfox.guava_preconditions.Preconditions.checkNotNull;

/**
 * {@link SharedPreferences} implementation of {@link RxPrefer}.
 */
public class SharedPreferencesRxPrefer extends AndroidRxPrefer {

    /**
     * References the actual shared preferences instance.
     */
    protected final SharedPreferences preferences;

    //----------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //----------------------------------------------------------------------------------------------

    public SharedPreferencesRxPrefer(SharedPreferences preferences) {
        this.preferences = checkNotNull(preferences, "Preferences can not be null");
    }

    //----------------------------------------------------------------------------------------------
    // PROTECTED METHODS
    //----------------------------------------------------------------------------------------------

    @Override
    protected PreferHelper createHelper() {
        return new SharedPreferencesHelper(preferences, new OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences ignored, String serializedKey) {
                handlePrefChanged(serializedKey);
            }
        });
    }

}

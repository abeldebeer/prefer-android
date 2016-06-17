package com.cookingfox.android.prefer.impl.prefer;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

import com.cookingfox.android.prefer.api.prefer.Prefer;

import static com.cookingfox.guava_preconditions.Preconditions.checkNotNull;

/**
 * {@link SharedPreferences} implementation of {@link Prefer}.
 */
public class SharedPreferencesPrefer extends AndroidPrefer {

    /**
     * References the actual shared preferences instance.
     */
    protected final SharedPreferences preferences;

    //----------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //----------------------------------------------------------------------------------------------

    public SharedPreferencesPrefer(SharedPreferences preferences) {
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

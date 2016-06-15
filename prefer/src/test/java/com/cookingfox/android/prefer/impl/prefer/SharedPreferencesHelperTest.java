package com.cookingfox.android.prefer.impl.prefer;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

import com.cookingfox.android.prefer_testing.shared_preferences.InMemorySharedPreferences;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link SharedPreferencesHelper}.
 */
public class SharedPreferencesHelperTest {

    private SharedPreferencesHelper helper;

    @Before
    public void setUp() throws Exception {
        helper = new SharedPreferencesHelper(new InMemorySharedPreferences(), onChangeListener);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: getFromString
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void getFromString_should_throw_if_key_null() throws Exception {
        helper.getFromString(null, null);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: putFromString
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void putFromString_should_throw_if_key_null() throws Exception {
        helper.putFromString(null, null);
    }

    //----------------------------------------------------------------------------------------------
    // HELPERS
    //----------------------------------------------------------------------------------------------

    final OnSharedPreferenceChangeListener onChangeListener = new OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            throw new UnsupportedOperationException("On change listener not implemented");
        }
    };

}

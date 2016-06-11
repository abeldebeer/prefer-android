package com.cookingfox.android.prefer.impl.prefer;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

import com.cookingfox.android.prefer.api.pref.Pref;
import com.cookingfox.android.prefer.api.pref.PrefListener;
import com.cookingfox.android.prefer.api.prefer.Prefer;

import java.util.Map;
import java.util.Set;

/**
 * Created by abeldebeer on 10/05/16.
 */
public class SharedPreferencesPrefer extends AndroidPrefer {

    protected final SharedPreferences preferences;

    //----------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //----------------------------------------------------------------------------------------------

    public SharedPreferencesPrefer(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    //----------------------------------------------------------------------------------------------
    // LIFECYCLE
    //----------------------------------------------------------------------------------------------

    @Override
    public void initializePrefer() {
        preferences.registerOnSharedPreferenceChangeListener(onChangeListener);
    }

    @Override
    public void disposePrefer() {
        preferences.unregisterOnSharedPreferenceChangeListener(onChangeListener);
    }

    //----------------------------------------------------------------------------------------------
    // BOOLEAN
    //----------------------------------------------------------------------------------------------

    @Override
    public Boolean getBoolean(Enum key, Boolean defaultValue) {
        return Boolean.parseBoolean(getFromString(key, defaultValue));
    }

    @Override
    public Prefer putBoolean(Enum key, Boolean value) {
        return putFromString(key, value);
    }

    //----------------------------------------------------------------------------------------------
    // INTEGER
    //----------------------------------------------------------------------------------------------

    @Override
    public Integer getInteger(Enum key, Integer defaultValue) {
        return Integer.parseInt(getFromString(key, defaultValue));
    }

    @Override
    public Prefer putInteger(Enum key, Integer value) {
        return putFromString(key, value);
    }

    //----------------------------------------------------------------------------------------------
    // STRING
    //----------------------------------------------------------------------------------------------

    @Override
    public String getString(Enum key, String defaultValue) {
        return getFromString(key, defaultValue);
    }

    @Override
    public Prefer putString(Enum key, String value) {
        return putFromString(key, value);
    }

    //----------------------------------------------------------------------------------------------
    // PROTECTED METHODS
    //----------------------------------------------------------------------------------------------

    protected <T> String getFromString(Enum key, T defaultValue) {
        final String stringKey = PreferKeySerializer.serializeKey(key);
        final String stringDefaultValue = String.valueOf(defaultValue);

        return preferences.getString(stringKey, stringDefaultValue);
    }

    protected <T> Prefer putFromString(Enum key, T value) {
        final String stringKey = PreferKeySerializer.serializeKey(key);
        final String stringValue = String.valueOf(value);

        preferences.edit()
                .putString(stringKey, stringValue)
                .apply();

        return this;
    }

    //----------------------------------------------------------------------------------------------
    // IMPLEMENTATION: OnSharedPreferenceChangeListener
    //----------------------------------------------------------------------------------------------

    protected final OnSharedPreferenceChangeListener onChangeListener = new OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences ignored, String serializedKey) {
            Enum key;

            try {
                key = PreferKeySerializer.deserializeKey(serializedKey);
            } catch (ClassNotFoundException e) {
                // FIXME: 10/05/16 Handle class not found
                e.printStackTrace();
                return;
            }

            for (Map.Entry<Pref, Set<PrefListener>> entry : prefListeners.entrySet()) {
                Pref pref = entry.getKey();

                if (pref.getKey().equals(key)) {
                    Object value = pref.getValue();

                    for (PrefListener listener : entry.getValue()) {
                        listener.onValueChanged(value);
                    }

                    break;
                }
            }
        }
    };

}

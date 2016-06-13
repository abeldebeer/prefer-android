package com.cookingfox.android.prefer.impl.prefer;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

import com.cookingfox.android.prefer.api.exception.PreferException;
import com.cookingfox.android.prefer.api.pref.Pref;
import com.cookingfox.android.prefer.api.pref.PrefListener;

import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Prefer implementation using Android {@link SharedPreferences}.
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
        this.preferences = checkNotNull(preferences, "SharedPreferences can not be null");
    }

    //----------------------------------------------------------------------------------------------
    // LIFECYCLE
    //----------------------------------------------------------------------------------------------

    @Override
    public void initializePrefer() {
        preferences.registerOnSharedPreferenceChangeListener(onChangeListener);

        super.initializePrefer();
    }

    @Override
    public void disposePrefer() {
        preferences.unregisterOnSharedPreferenceChangeListener(onChangeListener);

        super.disposePrefer();
    }

    //----------------------------------------------------------------------------------------------
    // BOOLEAN
    //----------------------------------------------------------------------------------------------

    @Override
    public boolean getBoolean(Enum key, boolean defaultValue) {
        return Boolean.parseBoolean(getFromString(key, defaultValue));
    }

    @Override
    public void putBoolean(Enum key, boolean value) {
        putFromString(key, value);
    }

    //----------------------------------------------------------------------------------------------
    // INTEGER
    //----------------------------------------------------------------------------------------------

    @Override
    public int getInteger(Enum key, int defaultValue) {
        return Integer.parseInt(getFromString(key, defaultValue));
    }

    @Override
    public void putInteger(Enum key, int value) {
        putFromString(key, value);
    }

    //----------------------------------------------------------------------------------------------
    // STRING
    //----------------------------------------------------------------------------------------------

    @Override
    public String getString(Enum key, String defaultValue) {
        return getFromString(key, defaultValue);
    }

    @Override
    public void putString(Enum key, String value) {
        putFromString(key, value);
    }

    //----------------------------------------------------------------------------------------------
    // PROTECTED METHODS
    //----------------------------------------------------------------------------------------------

    /**
     * Attempts to fetch the value for this key from the shared preferences by converting to a
     * String value first. This makes the process more straight-forward, since preference values
     * are stored as Strings by default.
     *
     * @param key          The unique key for this preference.
     * @param defaultValue The default value to use if no stored value is available.
     * @param <T>          Indicates the concrete value type, e.g. `Boolean`.
     * @return The stored value as a String, or the default value as a String.
     */
    protected <T> String getFromString(Enum key, T defaultValue) {
        checkNotNull(key, "Preference key can not be null");

        final String stringKey = PreferKeySerializer.serializeKey(key);
        final String stringDefaultValue = String.valueOf(defaultValue);

        return preferences.getString(stringKey, stringDefaultValue);
    }

    /**
     * Attempts to store the provided value for the preference key. Converts the value to a String
     * first to make the process more straight-forward, since preference values are stored as
     * Strings by default.
     *
     * @param key   The unique key for this preference.
     * @param value The value to store for this preference.
     * @param <T>   Indicates the concrete value type, e.g. `Boolean`.
     */
    protected <T> void putFromString(Enum key, T value) {
        checkNotNull(key, "Preference key can not be null");

        final String stringKey = PreferKeySerializer.serializeKey(key);
        final String stringValue = String.valueOf(value);

        preferences.edit()
                .putString(stringKey, stringValue)
                .apply();
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
            } catch (ClassNotFoundException error) {
                // wrap the exception and print the stack trace
                String message = String.format("Could not deserialize Pref key: '%s'", serializedKey);
                new PreferException(message, error).printStackTrace();
                return;
            }

            for (Map.Entry<Pref, Set<PrefListener>> entry : prefListeners.entrySet()) {
                Pref pref = entry.getKey();

                // match pref by key
                if (pref.getKey().equals(key)) {
                    Object value = pref.getValue();

                    // pass new value to listeners
                    for (PrefListener listener : entry.getValue()) {
                        listener.onValueChanged(value);
                    }

                    break;
                }
            }
        }
    };

}

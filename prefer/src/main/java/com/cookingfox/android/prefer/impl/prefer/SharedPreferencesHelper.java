package com.cookingfox.android.prefer.impl.prefer;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.EditTextPreference;

import static com.cookingfox.guava_preconditions.Preconditions.checkNotNull;

/**
 * Helper class for working with Android {@link SharedPreferences}.
 */
public class SharedPreferencesHelper implements PreferHelper {

    /**
     * Listener that is executed when a SharedPreference value changes.
     */
    protected final OnSharedPreferenceChangeListener onChangeListener;

    /**
     * References the actual shared preferences instance.
     */
    protected final SharedPreferences preferences;

    //----------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //----------------------------------------------------------------------------------------------

    public SharedPreferencesHelper(SharedPreferences preferences,
                                   OnSharedPreferenceChangeListener listener) {
        this.onChangeListener = checkNotNull(listener, "Listener can not be null");
        this.preferences = checkNotNull(preferences, "Preferences can not be null");
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
    public boolean getBoolean(Enum key, boolean defaultValue) {
        // boolean is stored without conversion to string
        return preferences.getBoolean(serializeKey(key), defaultValue);
    }

    @Override
    public void putBoolean(Enum key, boolean value) {
        // boolean is stored without conversion to string
        preferences.edit()
                .putBoolean(serializeKey(key), value)
                .apply();
    }

    //----------------------------------------------------------------------------------------------
    // FLOAT
    //----------------------------------------------------------------------------------------------

    @Override
    public float getFloat(Enum key, float defaultValue) {
        return Float.parseFloat(getFromString(key, defaultValue));
    }

    @Override
    public void putFloat(Enum key, float value) {
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
    // LONG
    //----------------------------------------------------------------------------------------------

    @Override
    public long getLong(Enum key, long defaultValue) {
        return Long.parseLong(getFromString(key, defaultValue));
    }

    @Override
    public void putLong(Enum key, long value) {
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
     * Fetch the persisted value from the shared preferences. The {@link EditTextPreference} stores
     * user input (also numeric values) as strings, so it is more straightforward to assume here
     * that the stored value is a string.
     *
     * @param key          The unique key for this preference.
     * @param defaultValue The default value to use if no stored value is available.
     * @param <T>          Indicates the concrete value type, e.g. `Boolean`.
     * @return The stored value as a String, or the default value as a String.
     */
    protected <T> String getFromString(Enum key, T defaultValue) {
        final String stringKey = serializeKey(key);
        final String stringDefaultValue = String.valueOf(defaultValue);

        return preferences.getString(stringKey, stringDefaultValue);
    }

    /**
     * Persist the value to the shared preferences. The {@link EditTextPreference} stores user input
     * (also numeric values) as strings, so it is more straightforward to assume here that the
     * stored value is a string.
     *
     * @param key   The unique key for this preference.
     * @param value The value to store for this preference.
     * @param <T>   Indicates the concrete value type, e.g. `Boolean`.
     */
    protected <T> void putFromString(Enum key, T value) {
        final String stringKey = serializeKey(key);
        final String stringValue = String.valueOf(value);

        preferences.edit()
                .putString(stringKey, stringValue)
                .apply();
    }

    /**
     * Returns a serialized string representation of the enum key.
     *
     * @param key The enum value.
     * @return Serialized string.
     */
    protected String serializeKey(Enum key) {
        return PreferKeySerializer.serializeKey(checkNotNull(key, "Pref key can not be null"));
    }

}

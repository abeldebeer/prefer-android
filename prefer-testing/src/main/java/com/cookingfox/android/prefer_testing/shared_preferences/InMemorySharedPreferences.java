package com.cookingfox.android.prefer_testing.shared_preferences;

import android.content.SharedPreferences;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of {@link SharedPreferences} and {@link Editor} that uses a simple map for the
 * values.
 */
public class InMemorySharedPreferences implements SharedPreferences, SharedPreferences.Editor {

    /**
     * The current preference key for the {@link #put(String, Object)} and {@link #apply()}
     * operations.
     */
    protected String currentKeyForPut;

    /**
     * A map of the stored preference values, as strings.
     */
    public final Map<String, String> values = new LinkedHashMap<>();

    /**
     * Registered preference change listeners.
     */
    public final List<OnSharedPreferenceChangeListener> onChangeListeners = new LinkedList<>();

    //----------------------------------------------------------------------------------------------
    // PUBLIC METHODS
    //----------------------------------------------------------------------------------------------

    @Override
    public Map<String, ?> getAll() {
        return values;
    }

    @Override
    public String getString(String key, String defValue) {
        return get(key, defValue);
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defValues) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getInt(String key, int defValue) {
        return Integer.parseInt(get(key, defValue));
    }

    @Override
    public long getLong(String key, long defValue) {
        return Long.parseLong(get(key, defValue));
    }

    @Override
    public float getFloat(String key, float defValue) {
        return Float.parseFloat(get(key, defValue));
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return Boolean.parseBoolean(get(key, defValue));
    }

    @Override
    public boolean contains(String key) {
        return values.containsKey(key);
    }

    @Override
    public Editor edit() {
        return this;
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        onChangeListeners.add(listener);
    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        onChangeListeners.remove(listener);
    }

    @Override
    public Editor putString(String key, String value) {
        put(key, String.valueOf(value));
        return this;
    }

    @Override
    public Editor putStringSet(String key, Set<String> values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Editor putInt(String key, int value) {
        put(key, String.valueOf(value));
        return this;
    }

    @Override
    public Editor putLong(String key, long value) {
        put(key, String.valueOf(value));
        return this;
    }

    @Override
    public Editor putFloat(String key, float value) {
        put(key, String.valueOf(value));
        return this;
    }

    @Override
    public Editor putBoolean(String key, boolean value) {
        put(key, String.valueOf(value));
        return this;
    }

    @Override
    public Editor remove(String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Editor clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean commit() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void apply() {
        if (currentKeyForPut == null) {
            new RuntimeException("Current key for put is empty").printStackTrace();
            return;
        }

        for (OnSharedPreferenceChangeListener listener : onChangeListeners) {
            listener.onSharedPreferenceChanged(this, currentKeyForPut);
        }

        currentKeyForPut = null;
    }

    //----------------------------------------------------------------------------------------------
    // PROTECTED METHODS
    //----------------------------------------------------------------------------------------------

    /**
     * Retrieves the value from the map and uses `defValue` if no value is available.
     *
     * @param key      The preference key.
     * @param defValue The default value to use when no value is available.
     * @return The stored value as a String.
     */
    protected String get(String key, Object defValue) {
        String defValueString = String.valueOf(defValue);
        String value = values.get(key);

        return value == null ? defValueString : value;
    }

    /**
     * Converts the value to a string (more straight-forward storage) and saves it in the values
     * map.
     *
     * @param key   The preference key.
     * @param value The preference value.
     */
    protected void put(String key, Object value) {
        String previous = values.get(key);
        String valueString = String.valueOf(value);

        if (previous == null || !valueString.equals(String.valueOf(previous))) {
            currentKeyForPut = key;
            values.put(key, valueString);
        }
    }

}

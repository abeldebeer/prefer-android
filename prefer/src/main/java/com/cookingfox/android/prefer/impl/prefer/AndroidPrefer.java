package com.cookingfox.android.prefer.impl.prefer;

import com.cookingfox.android.prefer.api.exception.GroupAlreadyAddedException;
import com.cookingfox.android.prefer.api.exception.PreferException;
import com.cookingfox.android.prefer.api.exception.PreferNotInitializedException;
import com.cookingfox.android.prefer.api.pref.OnValueChanged;
import com.cookingfox.android.prefer.api.pref.Pref;
import com.cookingfox.android.prefer.api.pref.PrefGroup;
import com.cookingfox.android.prefer.api.prefer.Prefer;
import com.cookingfox.android.prefer.impl.pref.AndroidPrefGroup;
import com.cookingfox.android.prefer.impl.pref.typed.AndroidBooleanPref;
import com.cookingfox.android.prefer.impl.pref.typed.AndroidFloatPref;
import com.cookingfox.android.prefer.impl.pref.typed.AndroidIntegerPref;
import com.cookingfox.android.prefer.impl.pref.typed.AndroidLongPref;
import com.cookingfox.android.prefer.impl.pref.typed.AndroidStringPref;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Base implementation of {@link Prefer}.
 */
public abstract class AndroidPrefer implements Prefer {

    /**
     * Pref groups by key class.
     */
    protected final Map<Class, PrefGroup<? extends Enum>> groups = new LinkedHashMap<>();

    /**
     * Helper class.
     */
    protected PreferHelper helper;

    /**
     * Whether this Prefer is initialized: call {@link #initializePrefer()} first.
     */
    protected boolean initialized = false;

    /**
     * Pref subscribers per pref.
     */
    protected final Map<Pref, Set<OnValueChanged>> prefSubscribers = new LinkedHashMap<>();

    //----------------------------------------------------------------------------------------------
    // IMPLEMENTATION: PreferLifecycle
    //----------------------------------------------------------------------------------------------

    @Override
    public void initializePrefer() {
        getHelper().initializePrefer();

        initialized = true;
    }

    @Override
    public void disposePrefer() {
        getHelper().disposePrefer();

        groups.clear();
        prefSubscribers.clear();

        initialized = false;
    }

    //----------------------------------------------------------------------------------------------
    // BOOLEAN
    //----------------------------------------------------------------------------------------------

    @Override
    public boolean getBoolean(Enum key, boolean defaultValue) {
        return getHelper().getBoolean(key, defaultValue);
    }

    @Override
    public void putBoolean(Enum key, boolean value) {
        getHelper().putBoolean(key, value);
    }

    //----------------------------------------------------------------------------------------------
    // FLOAT
    //----------------------------------------------------------------------------------------------

    @Override
    public float getFloat(Enum key, float defaultValue) {
        return getHelper().getFloat(key, defaultValue);
    }

    @Override
    public void putFloat(Enum key, float value) {
        getHelper().putFloat(key, value);
    }

    //----------------------------------------------------------------------------------------------
    // INTEGER
    //----------------------------------------------------------------------------------------------

    @Override
    public int getInteger(Enum key, int defaultValue) {
        return getHelper().getInteger(key, defaultValue);
    }

    @Override
    public void putInteger(Enum key, int value) {
        getHelper().putInteger(key, value);
    }

    //----------------------------------------------------------------------------------------------
    // LONG
    //----------------------------------------------------------------------------------------------

    @Override
    public long getLong(Enum key, long defaultValue) {
        return getHelper().getLong(key, defaultValue);
    }

    @Override
    public void putLong(Enum key, long value) {
        getHelper().putLong(key, value);
    }

    //----------------------------------------------------------------------------------------------
    // STRING
    //----------------------------------------------------------------------------------------------

    @Override
    public String getString(Enum key, String defaultValue) {
        return getHelper().getString(key, defaultValue);
    }

    @Override
    public void putString(Enum key, String value) {
        getHelper().putString(key, value);
    }

    @Override
    public <K extends Enum<K>, V> void subscribe(Pref<K, V> pref, OnValueChanged<V> subscriber) {
        checkNotNull(pref, "Pref can not be null");
        checkNotNull(subscriber, "Subscriber can not be null");

        if (!initialized) {
            throw new PreferNotInitializedException("Can not add subscriber");
        }

        Set<OnValueChanged> subscribers = this.prefSubscribers.get(pref);

        if (subscribers == null) {
            subscribers = new LinkedHashSet<>();
            prefSubscribers.put(pref, subscribers);
        }

        subscribers.add(subscriber);
    }

    @Override
    public <K extends Enum<K>, V> void unsubscribe(Pref<K, V> pref, OnValueChanged<V> subscriber) {
        checkNotNull(pref, "Pref can not be null");
        checkNotNull(subscriber, "Subscriber can not be null");

        if (!initialized) {
            throw new PreferNotInitializedException("Can not add subscriber");
        }

        final Set<OnValueChanged> subscribers = this.prefSubscribers.get(pref);

        if (subscribers != null) {
            subscribers.remove(subscriber);
        }
    }

    @Override
    public <K extends Enum<K>> void addGroup(PrefGroup<K> group) {
        checkNotNull(group, "Group can not be null");

        Class<K> keyClass = group.getKeyClass();

        if (groups.containsKey(keyClass)) {
            throw new GroupAlreadyAddedException(group);
        }

        groups.put(keyClass, group);
    }

    @Override
    public <K extends Enum<K>> PrefGroup<K> findGroup(Class<K> keyClass) {
        // noinspection unchecked
        return (PrefGroup<K>) groups.get(checkNotNull(keyClass, "Key class can not be null"));
    }

    @Override
    public Set<PrefGroup<? extends Enum>> getGroups() {
        return new LinkedHashSet<>(groups.values());
    }

    //----------------------------------------------------------------------------------------------
    // PUBLIC METHODS
    //----------------------------------------------------------------------------------------------

    /**
     * Creates and adds a new Pref group for this key class.
     *
     * @param keyClass The enum key class for which to create a Pref group.
     * @param <K>      References the concrete enum key class.
     * @return The newly created group.
     * @see #addGroup(PrefGroup)
     */
    public <K extends Enum<K>> AndroidPrefGroup<K> addNewGroup(Class<K> keyClass) {
        AndroidPrefGroup<K> group = newGroup(keyClass);

        addGroup(group);

        return group;
    }

    /**
     * Creates a new Pref group for this key class.
     *
     * @param keyClass The enum key class for which to create a Pref group.
     * @param <K>      References the concrete enum key class.
     * @return The newly created group.
     * @see #addGroup(PrefGroup)
     */
    public <K extends Enum<K>> AndroidPrefGroup<K> newGroup(Class<K> keyClass) {
        return new AndroidPrefGroup<>(this, keyClass);
    }

    /**
     * Creates a new Pref with the provided key and default value.
     *
     * @param key          The Pref's unique key.
     * @param defaultValue The Pref's default value.
     * @param <K>          References the enum class for this Pref's key.
     * @return The newly created Pref.
     */
    public <K extends Enum<K>> AndroidBooleanPref<K> newBoolean(K key, boolean defaultValue) {
        return new AndroidBooleanPref<>(this, key, defaultValue);
    }

    /**
     * Creates a new Pref with the provided key and default value.
     *
     * @param key          The Pref's unique key.
     * @param defaultValue The Pref's default value.
     * @param <K>          References the enum class for this Pref's key.
     * @return The newly created Pref.
     */
    public <K extends Enum<K>> AndroidFloatPref<K> newFloat(K key, float defaultValue) {
        return new AndroidFloatPref<>(this, key, defaultValue);
    }

    /**
     * Creates a new Pref with the provided key and default value.
     *
     * @param key          The Pref's unique key.
     * @param defaultValue The Pref's default value.
     * @param <K>          References the enum class for this Pref's key.
     * @return The newly created Pref.
     */
    public <K extends Enum<K>> AndroidIntegerPref<K> newInteger(K key, int defaultValue) {
        return new AndroidIntegerPref<>(this, key, defaultValue);
    }

    /**
     * Creates a new Pref with the provided key and default value.
     *
     * @param key          The Pref's unique key.
     * @param defaultValue The Pref's default value.
     * @param <K>          References the enum class for this Pref's key.
     * @return The newly created Pref.
     */
    public <K extends Enum<K>> AndroidLongPref<K> newLong(K key, long defaultValue) {
        return new AndroidLongPref<>(this, key, defaultValue);
    }

    /**
     * Creates a new Pref with the provided key and default value.
     *
     * @param key          The Pref's unique key.
     * @param defaultValue The Pref's default value.
     * @param <K>          References the enum class for this Pref's key.
     * @return The new ly created Pref.
     */
    public <K extends Enum<K>> AndroidStringPref<K> newString(K key, String defaultValue) {
        return new AndroidStringPref<>(this, key, defaultValue);
    }

    //----------------------------------------------------------------------------------------------
    // PROTECTED METHODS
    //----------------------------------------------------------------------------------------------

    /**
     * Create a new Prefer helper instance.
     *
     * @return The create Prefer helper instance.
     */
    protected abstract PreferHelper createHelper();

    /**
     * Returns the helper instance. Creates a new helper using {@link #createHelper()} if it doesn't
     * exist yet.
     *
     * @return The Prefer helper instance.
     */
    protected PreferHelper getHelper() {
        if (helper == null) {
            helper = checkNotNull(createHelper(), "`createHelper` returned null");
        }

        return helper;
    }

    /**
     * Notify subscribers of a changed Pref.
     *
     * @param serializedKey The serialized Pref key.
     */
    protected void handlePrefChanged(String serializedKey) {
        Enum key;

        try {
            key = PreferKeySerializer.deserializeKey(serializedKey);
        } catch (ClassNotFoundException error) {
            // wrap the exception and print the stack trace
            String message = String.format("Could not deserialize Pref key: '%s'", serializedKey);
            new PreferException(message, error).printStackTrace();
            return;
        }

        for (Map.Entry<Pref, Set<OnValueChanged>> entry : prefSubscribers.entrySet()) {
            Pref pref = entry.getKey();

            // match pref by key
            if (pref.getKey().equals(key)) {
                Object value = pref.getValue();

                // pass new value to subscribers
                for (OnValueChanged subscriber : entry.getValue()) {
                    // noinspection unchecked
                    subscriber.onValueChanged(value);
                }

                break;
            }
        }
    }

}

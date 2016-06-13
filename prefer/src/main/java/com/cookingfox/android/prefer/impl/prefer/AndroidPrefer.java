package com.cookingfox.android.prefer.impl.prefer;

import com.cookingfox.android.prefer.api.exception.GroupAlreadyAddedException;
import com.cookingfox.android.prefer.api.exception.PreferNotInitializedException;
import com.cookingfox.android.prefer.api.pref.Pref;
import com.cookingfox.android.prefer.api.pref.PrefGroup;
import com.cookingfox.android.prefer.api.pref.PrefListener;
import com.cookingfox.android.prefer.api.prefer.Prefer;
import com.cookingfox.android.prefer.impl.pref.AndroidPrefGroup;
import com.cookingfox.android.prefer.impl.pref.typed.AndroidBooleanPref;
import com.cookingfox.android.prefer.impl.pref.typed.AndroidIntegerPref;
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
    protected final Map<Class<? extends Enum>, PrefGroup<? extends Enum>> groups = new LinkedHashMap<>();

    /**
     * Whether this Prefer is initialized: call {@link #initializePrefer()} first.
     */
    protected boolean initialized = false;

    /**
     * Pref listeners per pref.
     */
    protected final Map<Pref, Set<PrefListener>> prefListeners = new LinkedHashMap<>();

    //----------------------------------------------------------------------------------------------
    // IMPLEMENTATION: Prefer
    //----------------------------------------------------------------------------------------------

    @Override
    public void initializePrefer() {
        initialized = true;
    }

    @Override
    public void disposePrefer() {
        groups.clear();
        prefListeners.clear();

        initialized = false;
    }

    @Override
    public <K extends Enum<K>, V> Prefer addListener(Pref<K, V> pref, PrefListener<V> listener) {
        checkNotNull(pref, "Pref can not be null");
        checkNotNull(listener, "Listener can not be null");

        if (!initialized) {
            throw new PreferNotInitializedException("Can not add listener");
        }

        Set<PrefListener> listeners = this.prefListeners.get(pref);

        if (listeners == null) {
            listeners = new LinkedHashSet<>();
            prefListeners.put(pref, listeners);
        }

        listeners.add(listener);

        return this;
    }

    @Override
    public <K extends Enum<K>, V> Prefer removeListener(Pref<K, V> pref, PrefListener<V> listener) {
        checkNotNull(pref, "Pref can not be null");
        checkNotNull(listener, "Listener can not be null");

        if (!initialized) {
            throw new PreferNotInitializedException("Can not add listener");
        }

        final Set<PrefListener> listeners = this.prefListeners.get(pref);

        if (listeners != null) {
            listeners.remove(listener);
        }

        return this;
    }

    @Override
    public <K extends Enum<K>> Prefer addGroup(PrefGroup<K> group) {
        checkNotNull(group, "Group can not be null");

        Class<K> keyClass = group.getKeyClass();

        if (groups.containsKey(keyClass)) {
            throw new GroupAlreadyAddedException(group);
        }

        groups.put(keyClass, group);

        return this;
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
        AndroidPrefGroup<K> group = new AndroidPrefGroup<>(this, keyClass);

        addGroup(group);

        return group;
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
    public <K extends Enum<K>> AndroidIntegerPref<K> newInteger(K key, int defaultValue) {
        return new AndroidIntegerPref<>(this, key, defaultValue);
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

}

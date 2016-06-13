package com.cookingfox.android.prefer_rx.impl.pref;

import com.cookingfox.android.prefer.api.pref.PrefGroup;
import com.cookingfox.android.prefer.api.pref.PrefMeta;
import com.cookingfox.android.prefer.impl.pref.AndroidPrefGroup;
import com.cookingfox.android.prefer.impl.prefer.AndroidPrefer;
import com.cookingfox.android.prefer_rx.api.prefer.RxPrefer;
import com.cookingfox.android.prefer_rx.impl.pref.typed.AndroidBooleanRxPref;
import com.cookingfox.android.prefer_rx.impl.pref.typed.AndroidIntegerRxPref;
import com.cookingfox.android.prefer_rx.impl.pref.typed.AndroidStringRxPref;

/**
 * Implementation of {@link PrefGroup} with {@link PrefMeta}.
 *
 * @param <K> References the concrete enum key class.
 */
public class AndroidRxPrefGroup<K extends Enum<K>> extends AndroidPrefGroup<K> {

    /**
     * Reference to Prefer, so the current value can be retrieved and updated.
     */
    protected final RxPrefer rxPrefer;

    //----------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //----------------------------------------------------------------------------------------------

    /**
     * Create a new Pref group.
     *
     * @param prefer   Reference to Prefer, so the current value can be retrieved and updated.
     * @param keyClass The enum key class for the Prefs in this group.
     */
    public <T extends AndroidPrefer & RxPrefer> AndroidRxPrefGroup(T prefer, Class<K> keyClass) {
        super(prefer, keyClass);

        rxPrefer = prefer;
    }

    //----------------------------------------------------------------------------------------------
    // PUBLIC METHODS
    //----------------------------------------------------------------------------------------------

    /**
     * Creates a new Pref with the provided key and default value.
     *
     * @param key          The Pref's unique key.
     * @param defaultValue The Pref's default value.
     * @return The newly created Pref.
     */
    public AndroidBooleanRxPref<K> addNewBoolean(K key, boolean defaultValue) {
        return addNewPref(new AndroidBooleanRxPref<>(rxPrefer, key, defaultValue));
    }

    /**
     * Creates a new Pref with the provided key and default value.
     *
     * @param key          The Pref's unique key.
     * @param defaultValue The Pref's default value.
     * @return The newly created Pref.
     */
    public AndroidIntegerRxPref<K> addNewInteger(K key, int defaultValue) {
        return addNewPref(new AndroidIntegerRxPref<>(rxPrefer, key, defaultValue));
    }

    /**
     * Creates a new Pref with the provided key and default value.
     *
     * @param key          The Pref's unique key.
     * @param defaultValue The Pref's default value.
     * @return The new ly created Pref.
     */
    public AndroidStringRxPref<K> addNewString(K key, String defaultValue) {
        return addNewPref(new AndroidStringRxPref<>(rxPrefer, key, defaultValue));
    }

}

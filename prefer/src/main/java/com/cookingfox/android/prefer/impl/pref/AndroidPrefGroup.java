package com.cookingfox.android.prefer.impl.pref;

import com.cookingfox.android.prefer.api.exception.IncorrectPrefKeyClassException;
import com.cookingfox.android.prefer.api.exception.PrefAlreadyAddedException;
import com.cookingfox.android.prefer.api.pref.Pref;
import com.cookingfox.android.prefer.api.pref.PrefGroup;
import com.cookingfox.android.prefer.api.pref.PrefMeta;
import com.cookingfox.android.prefer.impl.pref.typed.AndroidBooleanPref;
import com.cookingfox.android.prefer.impl.pref.typed.AndroidIntegerPref;
import com.cookingfox.android.prefer.impl.pref.typed.AndroidLongPref;
import com.cookingfox.android.prefer.impl.pref.typed.AndroidStringPref;
import com.cookingfox.android.prefer.impl.prefer.AndroidPrefer;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation of {@link PrefGroup} with {@link PrefMeta}.
 *
 * @param <K> References the concrete enum key class.
 */
public class AndroidPrefGroup<K extends Enum<K>>
        extends AbstractPrefMeta
        implements PrefGroup<K> {

    /**
     * The enum key class for the Prefs in this group.
     */
    protected final Class<K> keyClass;

    /**
     * Reference to Prefer, so the current value can be retrieved and updated.
     */
    protected final AndroidPrefer prefer;

    /**
     * A map of prefs by their unique key.
     */
    protected final Map<K, Pref<K, ?>> prefs = new LinkedHashMap<>();

    //----------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //----------------------------------------------------------------------------------------------

    /**
     * Create a new Pref group.
     *
     * @param prefer   Reference to Prefer, so the current value can be retrieved and updated.
     * @param keyClass The enum key class for the Prefs in this group.
     */
    public AndroidPrefGroup(AndroidPrefer prefer, Class<K> keyClass) {
        this.keyClass = checkNotNull(keyClass, "Group key class can not be null");
        this.prefer = checkNotNull(prefer, "Prefer can not be null");
    }

    //----------------------------------------------------------------------------------------------
    // IMPLEMENTATION: PrefGroup
    //----------------------------------------------------------------------------------------------

    @Override
    public void addPref(Pref<K, ?> pref) {
        K prefKey = pref.getKey();

        if (prefs.containsKey(prefKey)) {
            // pref already added
            throw new PrefAlreadyAddedException(pref);
        } else if (!keyClass.isInstance(pref.getKey())) {
            // pref key is not an instance of this group's key class
            throw new IncorrectPrefKeyClassException(pref, this);
        }

        prefs.put(prefKey, pref);
    }

    @Override
    public Pref<K, ?> findPref(K key) {
        return prefs.get(checkNotNull(key, "Key can not be null"));
    }

    @Override
    public <V> Pref<K, V> findPref(K key, Class<V> valueClass) {
        checkNotNull(valueClass, "Value class can not be null");

        Pref found = findPref(key);

        // match found pref value class
        if (found != null && valueClass.isInstance(found.getDefaultValue())) {
            // noinspection unchecked
            return found;
        }

        return null;
    }

    @Override
    public Class<K> getKeyClass() {
        return keyClass;
    }

    @Override
    public Iterator<Pref<K, ?>> iterator() {
        return prefs.values().iterator();
    }

    //----------------------------------------------------------------------------------------------
    // PUBLIC METHODS
    //----------------------------------------------------------------------------------------------

    /**
     * Creates and adds a new Pref with the provided key and default value.
     *
     * @param key          The Pref's unique key.
     * @param defaultValue The Pref's default value.
     * @return The newly created Pref.
     */
    public AndroidBooleanPref<K> addNewBoolean(K key, boolean defaultValue) {
        return addNewPref(new AndroidBooleanPref<>(prefer, key, defaultValue));
    }

    /**
     * Creates and adds a new Pref with the provided key and default value.
     *
     * @param key          The Pref's unique key.
     * @param defaultValue The Pref's default value.
     * @return The newly created Pref.
     */
    public AndroidIntegerPref<K> addNewInteger(K key, int defaultValue) {
        return addNewPref(new AndroidIntegerPref<>(prefer, key, defaultValue));
    }

    /**
     * Creates and adds a new Pref with the provided key and default value.
     *
     * @param key          The Pref's unique key.
     * @param defaultValue The Pref's default value.
     * @return The newly created Pref.
     */
    public AndroidLongPref<K> addNewLong(K key, long defaultValue) {
        return addNewPref(new AndroidLongPref<>(prefer, key, defaultValue));
    }

    /**
     * Creates and adds a new Pref with the provided key and default value.
     *
     * @param key          The Pref's unique key.
     * @param defaultValue The Pref's default value.
     * @return The new ly created Pref.
     */
    public AndroidStringPref<K> addNewString(K key, String defaultValue) {
        return addNewPref(new AndroidStringPref<>(prefer, key, defaultValue));
    }

    //----------------------------------------------------------------------------------------------
    // PROTECTED METHODS
    //----------------------------------------------------------------------------------------------

    /**
     * Add the pref to the group.
     *
     * @param pref The Pref to add.
     * @param <T>  Indicates the concrete Pref type.
     * @return The pref.
     */
    protected <T extends Pref> T addNewPref(T pref) {
        // noinspection unchecked
        addPref(pref);

        return pref;
    }

}

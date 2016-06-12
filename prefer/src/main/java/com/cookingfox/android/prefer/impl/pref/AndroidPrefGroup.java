package com.cookingfox.android.prefer.impl.pref;

import com.cookingfox.android.prefer.api.pref.Pref;
import com.cookingfox.android.prefer.api.pref.PrefGroup;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by abeldebeer on 10/05/16.
 */
public class AndroidPrefGroup<K extends Enum<K>>
        extends AbstractPrefMeta<AndroidPrefGroup<K>>
        implements PrefGroup<K> {

    protected final Class<K> keyClass;
    protected final Map<K, Pref<K, ?>> prefs = new LinkedHashMap<>();

    //----------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //----------------------------------------------------------------------------------------------

    public AndroidPrefGroup(Class<K> keyClass) {
        this.keyClass = checkNotNull(keyClass, "Group key class can not be null");
    }

    //----------------------------------------------------------------------------------------------
    // PUBLIC METHODS
    //----------------------------------------------------------------------------------------------

    @Override
    public PrefGroup<K> add(Pref<K, ?> pref) {
        prefs.put(pref.getKey(), pref);

        return this;
    }

    @Override
    public Pref<K, ?> find(K key) {
        return prefs.get(key);
    }

    @Override
    public <V> Pref<K, V> find(K key, Class<V> valueClass) {
        Pref found = find(key);

        if (found != null && valueClass.isInstance(found.getDefaultValue())) {
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

}

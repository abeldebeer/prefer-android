package com.cookingfox.android.prefer.impl.pref;

import com.cookingfox.android.prefer.api.pref.Pref;
import com.cookingfox.android.prefer.api.pref.PrefGroup;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by abeldebeer on 10/05/16.
 */
public class AndroidPrefGroup<K extends Enum<K>>
        extends AbstractPrefMeta<AndroidPrefGroup<K>>
        implements PrefGroup<K> {

    protected final Map<K, Pref<K, ?>> prefs = new LinkedHashMap<>();

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
    public Iterator<Pref<K, ?>> iterator() {
        return prefs.values().iterator();
    }

}

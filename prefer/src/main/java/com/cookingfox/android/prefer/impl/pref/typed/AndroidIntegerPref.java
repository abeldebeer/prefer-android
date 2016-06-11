package com.cookingfox.android.prefer.impl.pref.typed;

import com.cookingfox.android.prefer.api.pref.Pref;
import com.cookingfox.android.prefer.api.pref.typed.IntegerPref;
import com.cookingfox.android.prefer.api.prefer.Prefer;
import com.cookingfox.android.prefer.impl.pref.AbstractAndroidPref;

/**
 * Created by abeldebeer on 10/05/16.
 */
public class AndroidIntegerPref<K extends Enum<K>>
        extends AbstractAndroidPref<K, Integer>
        implements IntegerPref<K> {

    public AndroidIntegerPref(Prefer prefer, K key, Integer defaultValue) {
        super(prefer, key, defaultValue);
    }

    @Override
    public Integer getValue() {
        return prefer.getInteger(key, defaultValue);
    }

    @Override
    public Pref<K, Integer> setValue(Integer value) throws Exception {
        if (validate(value)) {
            prefer.putInteger(key, value);
        }

        return this;
    }

}

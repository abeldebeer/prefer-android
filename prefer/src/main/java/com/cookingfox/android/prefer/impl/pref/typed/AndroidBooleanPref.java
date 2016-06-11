package com.cookingfox.android.prefer.impl.pref.typed;

import com.cookingfox.android.prefer.api.pref.Pref;
import com.cookingfox.android.prefer.api.pref.typed.BooleanPref;
import com.cookingfox.android.prefer.api.prefer.Prefer;
import com.cookingfox.android.prefer.impl.pref.AbstractAndroidPref;

/**
 * Created by abeldebeer on 10/05/16.
 */
public class AndroidBooleanPref<K extends Enum<K>>
        extends AbstractAndroidPref<K, Boolean>
        implements BooleanPref<K> {

    public AndroidBooleanPref(Prefer prefer, K key, Boolean defaultValue) {
        super(prefer, key, defaultValue);
    }

    @Override
    public Boolean getValue() {
        return prefer.getBoolean(key, defaultValue);
    }

    @Override
    public Pref<K, Boolean> setValue(Boolean value) throws Exception {
        if (validate(value)) {
            prefer.putBoolean(key, value);
        }

        return this;
    }

}

package com.cookingfox.android.prefer.impl.pref.typed;

import com.cookingfox.android.prefer.api.pref.Pref;
import com.cookingfox.android.prefer.api.pref.typed.StringPref;
import com.cookingfox.android.prefer.api.prefer.Prefer;
import com.cookingfox.android.prefer.impl.pref.AbstractAndroidPref;

/**
 * Created by abeldebeer on 10/05/16.
 */
public class AndroidStringPref<K extends Enum<K>>
        extends AbstractAndroidPref<K, String>
        implements StringPref<K> {

    public AndroidStringPref(Prefer prefer, K key, String defaultValue) {
        super(prefer, key, defaultValue);
    }

    @Override
    public String getValue() {
        return prefer.getString(key, defaultValue);
    }

    @Override
    public Pref<K, String> setValue(String value) throws Exception {
        if (validate(value)) {
            prefer.putString(key, value);
        }

        return this;
    }

}

package com.cookingfox.android.prefer.impl.pref.typed;

import com.cookingfox.android.prefer.api.exception.InvalidPrefValueException;
import com.cookingfox.android.prefer.api.pref.Pref;
import com.cookingfox.android.prefer.api.pref.typed.StringPref;
import com.cookingfox.android.prefer.api.prefer.Prefer;
import com.cookingfox.android.prefer.impl.pref.AbstractAndroidPref;

/**
 * {@link Pref} implementation with a String value.
 *
 * @param <K> References the enum class for this Pref's key.
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
    public Pref<K, String> setValue(String value) throws InvalidPrefValueException {
        try {
            validate(value);
            prefer.putString(key, value);
        } catch (Exception e) {
            throw new InvalidPrefValueException("Invalid String value: " + value, e);
        }

        return this;
    }

}

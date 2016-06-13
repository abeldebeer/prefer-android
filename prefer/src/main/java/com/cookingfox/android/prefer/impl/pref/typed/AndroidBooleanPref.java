package com.cookingfox.android.prefer.impl.pref.typed;

import com.cookingfox.android.prefer.api.exception.InvalidPrefValueException;
import com.cookingfox.android.prefer.api.pref.Pref;
import com.cookingfox.android.prefer.api.pref.typed.BooleanPref;
import com.cookingfox.android.prefer.api.prefer.Prefer;
import com.cookingfox.android.prefer.impl.pref.AbstractAndroidPref;

/**
 * {@link Pref} implementation with a boolean value.
 *
 * @param <K> References the enum class for this Pref's key.
 */
public class AndroidBooleanPref<K extends Enum<K>>
        extends AbstractAndroidPref<K, Boolean>
        implements BooleanPref<K> {

    public AndroidBooleanPref(Prefer prefer, K key, boolean defaultValue) {
        super(prefer, key, defaultValue);
    }

    @Override
    public Boolean getValue() {
        return prefer.getBoolean(key, defaultValue);
    }

    @Override
    public void setValue(Boolean value) throws InvalidPrefValueException {
        try {
            validate(value);
            prefer.putBoolean(key, value);
        } catch (Exception e) {
            throw new InvalidPrefValueException("Invalid boolean value: " + value, e);
        }
    }

}

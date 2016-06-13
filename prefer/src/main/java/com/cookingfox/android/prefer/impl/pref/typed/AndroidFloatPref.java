package com.cookingfox.android.prefer.impl.pref.typed;

import com.cookingfox.android.prefer.api.exception.InvalidPrefValueException;
import com.cookingfox.android.prefer.api.pref.Pref;
import com.cookingfox.android.prefer.api.pref.typed.FloatPref;
import com.cookingfox.android.prefer.api.prefer.Prefer;
import com.cookingfox.android.prefer.impl.pref.AbstractAndroidPref;

/**
 * {@link Pref} implementation with a float value.
 *
 * @param <K> References the enum class for this Pref's key.
 */
public class AndroidFloatPref<K extends Enum<K>>
        extends AbstractAndroidPref<K, Float>
        implements FloatPref<K> {

    public AndroidFloatPref(Prefer prefer, K key, float defaultValue) {
        super(prefer, key, defaultValue);
    }

    @Override
    public Float getValue() {
        return prefer.getFloat(key, defaultValue);
    }

    @Override
    public void setValue(Float value) throws InvalidPrefValueException {
        try {
            validate(value);
            prefer.putFloat(key, value);
        } catch (Exception e) {
            throw new InvalidPrefValueException("Invalid float value: " + value, e);
        }
    }

}

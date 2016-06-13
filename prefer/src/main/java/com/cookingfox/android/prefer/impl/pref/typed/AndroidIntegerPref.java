package com.cookingfox.android.prefer.impl.pref.typed;

import com.cookingfox.android.prefer.api.exception.InvalidPrefValueException;
import com.cookingfox.android.prefer.api.pref.Pref;
import com.cookingfox.android.prefer.api.pref.typed.IntegerPref;
import com.cookingfox.android.prefer.api.prefer.Prefer;
import com.cookingfox.android.prefer.impl.pref.AbstractAndroidPref;

/**
 * {@link Pref} implementation with an integer value.
 *
 * @param <K> References the enum class for this Pref's key.
 */
public class AndroidIntegerPref<K extends Enum<K>>
        extends AbstractAndroidPref<K, Integer>
        implements IntegerPref<K> {

    public AndroidIntegerPref(Prefer prefer, K key, int defaultValue) {
        super(prefer, key, defaultValue);
    }

    @Override
    public Integer getValue() {
        return prefer.getInteger(key, defaultValue);
    }

    @Override
    public void setValue(Integer value) throws InvalidPrefValueException {
        try {
            validate(value);
            prefer.putInteger(key, value);
        } catch (Exception e) {
            throw new InvalidPrefValueException("Invalid integer value: " + value, e);
        }
    }

}

package com.cookingfox.android.prefer.impl.pref.typed;

import com.cookingfox.android.prefer.api.exception.InvalidPrefValueException;
import com.cookingfox.android.prefer.api.pref.Pref;
import com.cookingfox.android.prefer.api.pref.typed.LongPref;
import com.cookingfox.android.prefer.api.prefer.Prefer;
import com.cookingfox.android.prefer.impl.pref.AbstractAndroidPref;

/**
 * {@link Pref} implementation with a long value.
 *
 * @param <K> References the enum class for this Pref's key.
 */
public class AndroidLongPref<K extends Enum<K>>
        extends AbstractAndroidPref<K, Long>
        implements LongPref<K> {

    public AndroidLongPref(Prefer prefer, K key, long defaultValue) {
        super(prefer, key, defaultValue);
    }

    @Override
    public Long getValue() {
        return prefer.getLong(key, defaultValue);
    }

    @Override
    public void setValue(Long value) throws InvalidPrefValueException {
        try {
            validate(value);
            prefer.putLong(key, value);
        } catch (Exception e) {
            throw new InvalidPrefValueException("Invalid long value: " + value, e);
        }
    }

}

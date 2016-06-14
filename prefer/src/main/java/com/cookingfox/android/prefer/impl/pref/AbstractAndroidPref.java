package com.cookingfox.android.prefer.impl.pref;

import android.preference.Preference;

import com.cookingfox.android.prefer.api.exception.InvalidPrefValueException;
import com.cookingfox.android.prefer.api.pref.OnValueChanged;
import com.cookingfox.android.prefer.api.pref.Pref;
import com.cookingfox.android.prefer.api.pref.PrefMeta;
import com.cookingfox.android.prefer.api.pref.PrefValidator;
import com.cookingfox.android.prefer.api.prefer.Prefer;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Abstract implementation of {@link Pref}, with {@link PrefMeta}.
 *
 * @param <K> References the enum class for this Pref's key.
 * @param <V> Indicates this Pref's value type.
 */
public abstract class AbstractAndroidPref<K extends Enum<K>, V>
        extends AbstractPrefMeta
        implements Pref<K, V>, PreferenceModifier {

    //----------------------------------------------------------------------------------------------
    // PROPERTIES
    //----------------------------------------------------------------------------------------------

    /**
     * Default Pref value.
     */
    protected final V defaultValue;

    /**
     * Unique Pref key.
     */
    protected final K key;

    /**
     * Reference to Prefer.
     */
    protected final Prefer prefer;

    /**
     * Android Preference component modifier.
     */
    protected PreferenceModifier preferenceModifier;

    /**
     * Pref value validator.
     */
    protected PrefValidator<V> validator;

    //----------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //----------------------------------------------------------------------------------------------

    /**
     * Create a new Pref.
     *
     * @param prefer       Reference to Prefer, so the current value can be retrieved and updated.
     * @param key          The enum key for this Pref.
     * @param defaultValue The default value for this Pref.
     * @throws InvalidPrefValueException when the default value is invalid.
     */
    public AbstractAndroidPref(Prefer prefer, K key, V defaultValue) {
        this.key = checkNotNull(key, "Key can not be null");
        this.prefer = checkNotNull(prefer, "Prefer can not be null");

        try {
            validate(defaultValue);

            this.defaultValue = defaultValue;
        } catch (Exception e) {
            throw new InvalidPrefValueException("Invalid default value: " + defaultValue, e);
        }
    }

    //----------------------------------------------------------------------------------------------
    // IMPLEMENTATION: Pref
    //----------------------------------------------------------------------------------------------

    @Override
    public void subscribe(OnValueChanged<V> subscriber) {
        prefer.subscribe(this, checkNotNull(subscriber, "Subscriber can not be null"));
    }

    @Override
    public V getDefaultValue() {
        return defaultValue;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public void unsubscribe(OnValueChanged<V> subscriber) {
        prefer.unsubscribe(this, subscriber);
    }

    @Override
    public void setValidator(PrefValidator<V> validator) {
        this.validator = checkNotNull(validator);
    }

    @Override
    public boolean validate(V value) throws Exception {
        if (validator == null) {
            checkNotNull(value);

            return true;
        }

        return validator.validate(value);
    }

    //----------------------------------------------------------------------------------------------
    // IMPLEMENTATION: PreferenceModifier
    //----------------------------------------------------------------------------------------------

    @Override
    public Preference modifyPreference(Preference generated) {
        return preferenceModifier == null ?
                generated :
                preferenceModifier.modifyPreference(generated);
    }

    //----------------------------------------------------------------------------------------------
    // SETTERS
    //----------------------------------------------------------------------------------------------

    /**
     * Set the preference modifier.
     *
     * @param modifier The preference modifier to use.
     */
    public void setPreferenceModifier(PreferenceModifier modifier) {
        this.preferenceModifier = checkNotNull(modifier, "Modifier cannot be null");
    }

}

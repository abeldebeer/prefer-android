package com.cookingfox.android.prefer.impl.pref;

import android.preference.Preference;

import com.cookingfox.android.prefer.api.exception.InvalidPrefValueException;
import com.cookingfox.android.prefer.api.pref.Pref;
import com.cookingfox.android.prefer.api.pref.PrefListener;
import com.cookingfox.android.prefer.api.pref.PrefValidator;
import com.cookingfox.android.prefer.api.prefer.Prefer;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by abeldebeer on 10/05/16.
 */
public abstract class AbstractAndroidPref<K extends Enum<K>, V>
        extends AbstractPrefMeta<AbstractAndroidPref<K, V>>
        implements Pref<K, V>, PreferenceModifier {

    //----------------------------------------------------------------------------------------------
    // PROPERTIES
    //----------------------------------------------------------------------------------------------

    protected final V defaultValue;
    protected final K key;
    protected PreferenceModifier preferenceModifier;
    protected final Prefer prefer;
    protected PrefValidator<V> validator;

    //----------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //----------------------------------------------------------------------------------------------

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
    public Pref<K, V> addListener(PrefListener<V> listener) {
        prefer.addListener(this, listener);

        return this;
    }

    @Override
    public V getDefaultValue() {
        return defaultValue;
    }

    @Override
    public K getKey() {
        return key;
    }

//    @Override
//    public Observable<V> observeValueChanges() {
//        return Observable.create(new Observable.OnSubscribe<V>() {
//            @Override
//            public void call(final Subscriber<? super V> subscriber) {
//                final PrefListener<V> listener = new PrefListener<V>() {
//                    @Override
//                    public void onValueChanged(V value) {
//                        subscriber.onNext(value);
//                    }
//                };
//
//                addListener(listener);
//
//                Subscriptions.create(new Action0() {
//                    @Override
//                    public void call() {
//                        removeListener(listener);
//                    }
//                });
//            }
//        });
//    }

    @Override
    public Pref<K, V> removeListener(PrefListener<V> listener) {
        prefer.removeListener(this, listener);

        return this;
    }

    @Override
    public Pref<K, V> setValidator(PrefValidator<V> validator) {
        this.validator = validator;
        return this;
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
        return preferenceModifier == null ? generated : preferenceModifier.modifyPreference(generated);
    }

    //----------------------------------------------------------------------------------------------
    // SETTERS
    //----------------------------------------------------------------------------------------------

    public AbstractAndroidPref<K, V> setPreferenceModifier(PreferenceModifier modifier) {
        this.preferenceModifier = modifier;
        return this;
    }

}

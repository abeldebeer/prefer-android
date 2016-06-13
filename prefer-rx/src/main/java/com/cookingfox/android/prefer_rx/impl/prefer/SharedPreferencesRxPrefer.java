package com.cookingfox.android.prefer_rx.impl.prefer;

import android.content.SharedPreferences;

import com.cookingfox.android.prefer.api.exception.PreferNotInitializedException;
import com.cookingfox.android.prefer.api.pref.Pref;
import com.cookingfox.android.prefer.api.pref.PrefListener;
import com.cookingfox.android.prefer.impl.prefer.SharedPreferencesPrefer;
import com.cookingfox.android.prefer_rx.api.prefer.RxPrefer;
import com.cookingfox.android.prefer_rx.impl.pref.typed.AndroidBooleanRxPref;
import com.cookingfox.android.prefer_rx.impl.pref.typed.AndroidIntegerRxPref;
import com.cookingfox.android.prefer_rx.impl.pref.typed.AndroidStringRxPref;

import java.util.LinkedHashSet;
import java.util.Set;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Extends {@link SharedPreferencesPrefer} with Rx functionality.
 */
public class SharedPreferencesRxPrefer extends SharedPreferencesPrefer implements RxPrefer {

    protected final Set<Subscriber> subscribers = new LinkedHashSet<>();

    //----------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //----------------------------------------------------------------------------------------------

    public SharedPreferencesRxPrefer(SharedPreferences preferences) {
        super(preferences);
    }

    //----------------------------------------------------------------------------------------------
    // IMPLEMENTATION: RxPrefer
    //----------------------------------------------------------------------------------------------

    @Override
    public void disposePrefer() {
        // unsubscribe all
        for (Subscriber subscriber : subscribers) {
            subscriber.unsubscribe();
        }

        super.disposePrefer();
    }

    @Override
    public <K extends Enum<K>, V> Observable<V> observePref(final Pref<K, V> pref) {
        checkNotNull(pref, "Pref can not be null");

        if (!initialized) {
            throw new PreferNotInitializedException("Can not observe Pref");
        }

        return Observable.create(new Observable.OnSubscribe<V>() {
            @Override
            public void call(final Subscriber<? super V> subscriber) {
                final PrefListener<V> listener = new PrefListener<V>() {
                    @Override
                    public void onValueChanged(V value) {
                        subscriber.onNext(value);
                    }
                };

                addListener(pref, listener);

                subscriber.add(Subscriptions.create(new Action0() {
                    @Override
                    public void call() {
                        removeListener(pref, listener);
                        subscriber.onCompleted();
                    }
                }));

                subscribers.add(subscriber);
            }
        });
    }

    //----------------------------------------------------------------------------------------------
    // PUBLIC METHODS
    //----------------------------------------------------------------------------------------------

    @Override
    public <K extends Enum<K>> AndroidBooleanRxPref<K> newBoolean(K key, boolean defaultValue) {
        return new AndroidBooleanRxPref<>(this, key, defaultValue);
    }

    @Override
    public <K extends Enum<K>> AndroidIntegerRxPref<K> newInteger(K key, int defaultValue) {
        return new AndroidIntegerRxPref<>(this, key, defaultValue);
    }

    @Override
    public <K extends Enum<K>> AndroidStringRxPref<K> newString(K key, String defaultValue) {
        return new AndroidStringRxPref<>(this, key, defaultValue);
    }

}

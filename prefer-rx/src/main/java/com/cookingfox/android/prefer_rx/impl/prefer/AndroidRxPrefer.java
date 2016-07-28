package com.cookingfox.android.prefer_rx.impl.prefer;

import com.cookingfox.android.prefer.api.exception.PreferNotInitializedException;
import com.cookingfox.android.prefer.api.pref.OnValueChanged;
import com.cookingfox.android.prefer.api.pref.Pref;
import com.cookingfox.android.prefer.impl.prefer.AndroidPrefer;
import com.cookingfox.android.prefer_rx.api.prefer.RxPrefer;
import com.cookingfox.android.prefer_rx.impl.pref.AndroidRxPrefGroup;
import com.cookingfox.android.prefer_rx.impl.pref.typed.AndroidBooleanRxPref;
import com.cookingfox.android.prefer_rx.impl.pref.typed.AndroidFloatRxPref;
import com.cookingfox.android.prefer_rx.impl.pref.typed.AndroidIntegerRxPref;
import com.cookingfox.android.prefer_rx.impl.pref.typed.AndroidLongRxPref;
import com.cookingfox.android.prefer_rx.impl.pref.typed.AndroidStringRxPref;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.Subscriptions;

import static com.cookingfox.guava_preconditions.Preconditions.checkNotNull;

/**
 * Base implementation of {@link RxPrefer}.
 */
public abstract class AndroidRxPrefer extends AndroidPrefer implements RxPrefer {

    /**
     * Track observable subscribers, so they can be cleared on dispose.
     */
    protected final CompositeSubscription subscribers = new CompositeSubscription();

    //----------------------------------------------------------------------------------------------
    // IMPLEMENTATION: RxPrefer
    //----------------------------------------------------------------------------------------------

    @Override
    public <K extends Enum<K>, V> Observable<V> observeValueChanges(final Pref<K, V> pref) {
        checkNotNull(pref, "Pref can not be null");

        if (!initialized) {
            throw new PreferNotInitializedException("Can not observe Pref");
        }

        return Observable.create(new Observable.OnSubscribe<V>() {
            @Override
            public void call(final Subscriber<? super V> subscriber) {
                // create listener implementation that notifies Rx subscriber
                final OnValueChanged<V> listener = new OnValueChanged<V>() {
                    @Override
                    public void onValueChanged(V value) {
                        subscriber.onNext(value);
                    }
                };

                addValueChangedListener(pref, listener);

                // remove the listener when the Rx subscriber unsubscribes
                subscriber.add(Subscriptions.create(new Action0() {
                    @Override
                    public void call() {
                        removeValueChangedListener(pref, listener);
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
    public void disposePrefer() {
        // unsubscribe all
        subscribers.unsubscribe();

        super.disposePrefer();
    }

    @Override
    public <K extends Enum<K>> AndroidRxPrefGroup<K> addNewGroup(Class<K> keyClass) {
        AndroidRxPrefGroup<K> group = newGroup(keyClass);

        addGroup(group);

        return group;
    }

    @Override
    public <K extends Enum<K>> AndroidRxPrefGroup<K> newGroup(Class<K> keyClass) {
        return new AndroidRxPrefGroup<>(this, keyClass);
    }

    @Override
    public <K extends Enum<K>> AndroidBooleanRxPref<K> newBoolean(K key, boolean defaultValue) {
        return new AndroidBooleanRxPref<>(this, key, defaultValue);
    }

    @Override
    public <K extends Enum<K>> AndroidFloatRxPref<K> newFloat(K key, float defaultValue) {
        return new AndroidFloatRxPref<>(this, key, defaultValue);
    }

    @Override
    public <K extends Enum<K>> AndroidIntegerRxPref<K> newInteger(K key, int defaultValue) {
        return new AndroidIntegerRxPref<>(this, key, defaultValue);
    }

    @Override
    public <K extends Enum<K>> AndroidLongRxPref<K> newLong(K key, long defaultValue) {
        return new AndroidLongRxPref<>(this, key, defaultValue);
    }

    @Override
    public <K extends Enum<K>> AndroidStringRxPref<K> newString(K key, String defaultValue) {
        return new AndroidStringRxPref<>(this, key, defaultValue);
    }

}

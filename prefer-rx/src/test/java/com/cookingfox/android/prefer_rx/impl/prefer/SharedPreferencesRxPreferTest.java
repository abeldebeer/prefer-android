package com.cookingfox.android.prefer_rx.impl.prefer;

import com.cookingfox.android.prefer.api.exception.PreferNotInitializedException;
import com.cookingfox.android.prefer.impl.pref.typed.AndroidBooleanPref;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fixtures.FixtureSharedPreferences;
import fixtures.example.Key;
import rx.Subscription;
import rx.observers.TestSubscriber;

/**
 * Unit tests for {@link SharedPreferencesRxPrefer}.
 */
public class SharedPreferencesRxPreferTest {

    //----------------------------------------------------------------------------------------------
    // TEST SETUP
    //----------------------------------------------------------------------------------------------

    private SharedPreferencesRxPrefer prefer;

    @Before
    public void setUp() throws Exception {
        prefer = new SharedPreferencesRxPrefer(new FixtureSharedPreferences());

        prefer.initializePrefer();
    }

    @After
    public void tearDown() throws Exception {
        prefer.disposePrefer();
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: disposePrefer
    //----------------------------------------------------------------------------------------------

    @Test
    public void disposePrefer_should_unsubscribe_subscribers() throws Exception {
        TestSubscriber<Boolean> subscriber = TestSubscriber.create();

        AndroidBooleanPref<Key> pref = prefer.newBoolean(Key.IsEnabled, true);
        prefer.observePref(pref).subscribe(subscriber);

        prefer.disposePrefer();

        subscriber.assertCompleted();
        subscriber.assertUnsubscribed();
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: observePref
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void observePref_should_throw_if_pref_null() throws Exception {
        // noinspection unchecked
        prefer.observePref(null);
    }

    @Test(expected = PreferNotInitializedException.class)
    public void observePref_should_throw_if_not_initialized() throws Exception {
        SharedPreferencesRxPrefer prefer = new SharedPreferencesRxPrefer(new FixtureSharedPreferences());
        AndroidBooleanPref<Key> pref = prefer.newBoolean(Key.IsEnabled, true);

        prefer.observePref(pref);
    }

    @Test
    public void observePref_should_receive_value_changes() throws Exception {
        TestSubscriber<Boolean> subscriber = TestSubscriber.create();

        AndroidBooleanPref<Key> pref = prefer.newBoolean(Key.IsEnabled, true);
        prefer.observePref(pref).subscribe(subscriber);

        pref.setValue(!pref.getValue());

        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
    }

    @Test
    public void observePref_should_remove_subscriber_on_unsubscribe() throws Exception {
        TestSubscriber<Boolean> subscriber = TestSubscriber.create();

        AndroidBooleanPref<Key> pref = prefer.newBoolean(Key.IsEnabled, true);
        Subscription subscription = prefer.observePref(pref).subscribe(subscriber);

        pref.setValue(!pref.getValue());

        subscription.unsubscribe();

        pref.setValue(!pref.getValue());
        pref.setValue(!pref.getValue());

        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
    }

}

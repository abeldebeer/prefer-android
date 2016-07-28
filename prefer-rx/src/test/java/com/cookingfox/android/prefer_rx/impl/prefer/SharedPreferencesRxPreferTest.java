package com.cookingfox.android.prefer_rx.impl.prefer;

import com.cookingfox.android.prefer.api.exception.PreferNotInitializedException;
import com.cookingfox.android.prefer.impl.pref.typed.AndroidBooleanPref;
import com.cookingfox.android.prefer_testing.fixtures.Key;
import com.cookingfox.android.prefer_testing.shared_preferences.InMemorySharedPreferences;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
        prefer = new SharedPreferencesRxPrefer(new InMemorySharedPreferences());

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
        prefer.observeValueChanges(pref).subscribe(subscriber);

        prefer.disposePrefer();

        subscriber.assertCompleted();
        subscriber.assertUnsubscribed();
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: observeValueChanges
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void observeValueChanges_should_throw_if_pref_null() throws Exception {
        // noinspection unchecked
        prefer.observeValueChanges(null);
    }

    @Test(expected = PreferNotInitializedException.class)
    public void observeValueChanges_should_throw_if_not_initialized() throws Exception {
        SharedPreferencesRxPrefer prefer = new SharedPreferencesRxPrefer(new InMemorySharedPreferences());
        AndroidBooleanPref<Key> pref = prefer.newBoolean(Key.IsEnabled, true);

        prefer.observeValueChanges(pref);
    }

    @Test
    public void observeValueChanges_should_receive_value_changes() throws Exception {
        TestSubscriber<Boolean> subscriber = TestSubscriber.create();

        AndroidBooleanPref<Key> pref = prefer.newBoolean(Key.IsEnabled, true);
        prefer.observeValueChanges(pref).subscribe(subscriber);

        pref.setValue(!pref.getValue());

        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
    }

    @Test
    public void observeValueChanges_should_remove_subscriber_on_unsubscribe() throws Exception {
        TestSubscriber<Boolean> subscriber = TestSubscriber.create();

        AndroidBooleanPref<Key> pref = prefer.newBoolean(Key.IsEnabled, true);
        Subscription subscription = prefer.observeValueChanges(pref).subscribe(subscriber);

        pref.setValue(!pref.getValue());

        subscription.unsubscribe();

        pref.setValue(!pref.getValue());
        pref.setValue(!pref.getValue());

        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
    }

}

package com.cookingfox.android.prefer_rx.impl.pref.typed;

import com.cookingfox.android.prefer_rx.impl.prefer.SharedPreferencesRxPrefer;
import com.cookingfox.android.prefer_testing.fixtures.Key;
import com.cookingfox.android.prefer_testing.shared_preferences.InMemorySharedPreferences;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rx.observers.TestSubscriber;

/**
 * Unit tests for {@link AndroidStringRxPref}.
 */
public class AndroidStringRxPrefTest {

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
    // TESTS: observeValueChanges
    //----------------------------------------------------------------------------------------------

    @Test
    public void observeValueChanges_should_create_value_observable() throws Exception {
        AndroidBooleanRxPref<Key> pref = prefer.newBoolean(Key.IsEnabled, true);

        TestSubscriber<Boolean> subscriber = TestSubscriber.create();

        pref.observeValueChanges().subscribe(subscriber);

        pref.setValue(!pref.getValue());

        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
    }

}

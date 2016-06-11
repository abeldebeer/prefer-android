package com.cookingfox.android.prefer.impl.prefer;

import com.cookingfox.android.prefer.api.pref.PrefListener;
import com.cookingfox.android.prefer.api.pref.typed.BooleanPref;
import com.cookingfox.android.prefer.impl.pref.typed.AndroidBooleanPref;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import fixtures.FixtureSharedPreferences;
import fixtures.example.Key;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link SharedPreferencesPrefer}.
 */
public class SharedPreferencesPreferTest {

    //----------------------------------------------------------------------------------------------
    // TEST SETUP
    //----------------------------------------------------------------------------------------------

    private SharedPreferencesPrefer prefer;

    @Before
    public void setUp() throws Exception {
        prefer = new SharedPreferencesPrefer(new FixtureSharedPreferences());

        // add `OnSharedPreferenceChangeListener`
        prefer.initializePrefer();
    }

    @After
    public void tearDown() throws Exception {
        // remove `OnSharedPreferenceChangeListener`
        prefer.disposePrefer();
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: addListener
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void addListener_should_throw_if_null_pref() throws Exception {
        // noinspection unchecked
        prefer.addListener(null, new PrefListener<Boolean>() {
            @Override
            public void onValueChanged(Boolean value) {
                // ignored
            }
        });
    }

    @Test(expected = NullPointerException.class)
    public void addListener_should_throw_if_null_listener() throws Exception {
        prefer.addListener(createBooleanPref(true), null);
    }

    @Test
    public void addListener_should_call_listener_on_value_changed() throws Exception {
        BooleanPref<Key> booleanPref = createBooleanPref(false);
        final AtomicBoolean called = new AtomicBoolean(false);

        prefer.addListener(booleanPref, new PrefListener<Boolean>() {
            @Override
            public void onValueChanged(Boolean value) {
                called.set(true);
            }
        });

        booleanPref.setValue(true);

        assertTrue(called.get());
        assertTrue(booleanPref.getValue());
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: removeListener
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void removeListener_should_throw_if_null_prefer() throws Exception {
        // noinspection unchecked
        prefer.removeListener(null, new PrefListener<Boolean>() {
            @Override
            public void onValueChanged(Boolean value) {
                // ignored
            }
        });
    }

    @Test(expected = NullPointerException.class)
    public void removeListener_should_throw_if_null_listener() throws Exception {
        prefer.removeListener(createBooleanPref(true), null);
    }

    @Test
    public void removeListener_should_not_call_listener_on_value_changed() throws Exception {
        final AtomicBoolean called = new AtomicBoolean(false);

        BooleanPref<Key> booleanPref = createBooleanPref(false);
        PrefListener<Boolean> listener = new PrefListener<Boolean>() {
            @Override
            public void onValueChanged(Boolean value) {
                called.set(true);
            }
        };

        prefer.addListener(booleanPref, listener);
        prefer.removeListener(booleanPref, listener);

        booleanPref.setValue(true);

        assertFalse(called.get());
        assertTrue(booleanPref.getValue());
    }

    //----------------------------------------------------------------------------------------------
    // HELPER METHODS
    //----------------------------------------------------------------------------------------------

    private BooleanPref<Key> createBooleanPref(boolean defaultValue) {
        return new AndroidBooleanPref<>(prefer, Key.IsEnabled, defaultValue);
    }

}

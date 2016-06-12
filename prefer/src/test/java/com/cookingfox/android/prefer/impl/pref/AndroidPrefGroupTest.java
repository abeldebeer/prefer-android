package com.cookingfox.android.prefer.impl.pref;

import com.cookingfox.android.prefer.api.exception.IncorrectPrefKeyClassException;
import com.cookingfox.android.prefer.api.exception.PrefAlreadyAddedException;
import com.cookingfox.android.prefer.api.prefer.Prefer;
import com.cookingfox.android.prefer.impl.pref.typed.AndroidBooleanPref;
import com.cookingfox.android.prefer.impl.prefer.SharedPreferencesPrefer;

import org.junit.Before;
import org.junit.Test;

import fixtures.FixtureSharedPreferences;
import fixtures.example.Key;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link AndroidPrefGroup}.
 */
public class AndroidPrefGroupTest {

    //----------------------------------------------------------------------------------------------
    // TEST SETUP
    //----------------------------------------------------------------------------------------------

    private AndroidPrefGroup<Key> group;
    private Prefer prefer;

    @Before
    public void setUp() throws Exception {
        group = new AndroidPrefGroup<>(Key.class);
        prefer = new SharedPreferencesPrefer(new FixtureSharedPreferences());
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: constructor
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void constructor_should_throw_if_key_class_null() throws Exception {
        new AndroidPrefGroup<Key>(null);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: addPref
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void addPref_should_throw_if_pref_null() throws Exception {
        group.addPref(null);
    }

    @Test
    public void addPref_should_add_pref() throws Exception {
        AndroidBooleanPref<Key> pref = new AndroidBooleanPref<>(prefer, Key.IsEnabled, true);

        assertFalse(group.prefs.containsValue(pref));

        group.addPref(pref);

        assertTrue(group.prefs.containsValue(pref));
    }

    @Test(expected = PrefAlreadyAddedException.class)
    public void addPref_should_throw_if_pref_with_key_already_added() throws Exception {
        group.addPref(new AndroidBooleanPref<>(prefer, Key.IsEnabled, true));
        group.addPref(new AndroidBooleanPref<>(prefer, Key.IsEnabled, false));
    }

    @Test(expected = IncorrectPrefKeyClassException.class)
    public void addPref_should_throw_if_pref_incorrect_key_class() throws Exception {
        group.addPref(new AndroidBooleanPref(prefer, Thread.State.BLOCKED, true));
    }

}

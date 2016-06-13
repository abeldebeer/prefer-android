package com.cookingfox.android.prefer.impl.pref;

import com.cookingfox.android.prefer.api.exception.IncorrectPrefKeyClassException;
import com.cookingfox.android.prefer.api.exception.PrefAlreadyAddedException;
import com.cookingfox.android.prefer.api.pref.Pref;
import com.cookingfox.android.prefer.impl.pref.typed.AndroidBooleanPref;
import com.cookingfox.android.prefer.impl.pref.typed.AndroidIntegerPref;
import com.cookingfox.android.prefer.impl.pref.typed.AndroidStringPref;
import com.cookingfox.android.prefer.impl.prefer.AndroidPrefer;
import com.cookingfox.android.prefer.impl.prefer.SharedPreferencesPrefer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import fixtures.FixtureSharedPreferences;
import fixtures.example.Key;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link AndroidPrefGroup} and {@link AbstractPrefMeta}.
 */
public class AndroidPrefGroupTest {

    //----------------------------------------------------------------------------------------------
    // TEST SETUP
    //----------------------------------------------------------------------------------------------

    private AndroidPrefGroup<Key> group;
    private AndroidPrefer prefer;

    @Before
    public void setUp() throws Exception {
        prefer = new SharedPreferencesPrefer(new FixtureSharedPreferences());
        prefer.initializePrefer();

        group = new AndroidPrefGroup<>(prefer, Key.class);
    }

    @After
    public void tearDown() throws Exception {
        prefer.disposePrefer();
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: constructor
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void constructor_should_throw_if_prefer_null() throws Exception {
        new AndroidPrefGroup<>(null, Key.class);
    }

    @Test(expected = NullPointerException.class)
    public void constructor_should_throw_if_key_class_null() throws Exception {
        new AndroidPrefGroup<Key>(prefer, null);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: addNewBoolean
    //----------------------------------------------------------------------------------------------

    @Test
    public void addNewBoolean_should_create_and_add_boolean_pref() throws Exception {
        AndroidBooleanPref<Key> pref = group.addNewBoolean(Key.IsEnabled, true);

        assertNotNull(pref);
        assertTrue(group.prefs.containsValue(pref));
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: addNewInteger
    //----------------------------------------------------------------------------------------------

    @Test
    public void addNewInteger_should_create_and_add_integer_pref() throws Exception {
        AndroidIntegerPref<Key> pref = group.addNewInteger(Key.IntervalMs, 123);

        assertNotNull(pref);
        assertTrue(group.prefs.containsValue(pref));
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: addNewString
    //----------------------------------------------------------------------------------------------

    @Test
    public void addNewString_should_create_and_add_string_pref() throws Exception {
        AndroidStringPref<Key> pref = group.addNewString(Key.Username, "foo");

        assertNotNull(pref);
        assertTrue(group.prefs.containsValue(pref));
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
        // noinspection unchecked
        group.addPref(new AndroidBooleanPref(prefer, Thread.State.BLOCKED, true));
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: findPref (only key)
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void findPref_should_throw_if_key_null() throws Exception {
        group.findPref(null);
    }

    @Test
    public void findPref_should_return_null_if_not_present() throws Exception {
        Pref<Key, ?> result = group.findPref(Key.Username);

        assertNull(result);
    }

    @Test
    public void findPref_should_return_pref_if_present() throws Exception {
        Key key = Key.IsEnabled;
        AndroidBooleanPref<Key> pref = new AndroidBooleanPref<>(prefer, key, true);
        group.addPref(pref);

        Pref<Key, ?> result = group.findPref(key);

        assertSame(pref, result);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: findPref (key and value class)
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void findPrefWithValue_should_throw_if_key_null() throws Exception {
        group.findPref(null, String.class);
    }

    @Test(expected = NullPointerException.class)
    public void findPrefWithValue_should_throw_if_value_class_null() throws Exception {
        group.findPref(Key.Username, null);
    }

    @Test
    public void findPrefWithValue_should_return_null_if_not_present() throws Exception {
        Pref<Key, String> result = group.findPref(Key.Username, String.class);

        assertNull(result);
    }

    @Test
    public void findPrefWithValue_should_return_pref_if_present() throws Exception {
        Key key = Key.IsEnabled;
        AndroidBooleanPref<Key> pref = new AndroidBooleanPref<>(prefer, key, true);
        group.addPref(pref);

        Pref<Key, Boolean> result = group.findPref(key, Boolean.class);

        assertSame(pref, result);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: iterator
    //----------------------------------------------------------------------------------------------

    @Test
    public void iterator_should_return_iterator() throws Exception {
        Iterator<Pref<Key, ?>> iterator = group.iterator();

        assertNotNull(iterator);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: AbstractPrefMeta#enable
    //----------------------------------------------------------------------------------------------

    @Test
    public void enable_should_return_boolean() throws Exception {
        assertTrue(group.enable());

        group.setEnable(false);

        assertFalse(group.enable());
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: AbstractPrefMeta#show
    //----------------------------------------------------------------------------------------------

    @Test
    public void show_should_return_boolean() throws Exception {
        assertTrue(group.show());

        group.setShow(false);

        assertFalse(group.show());
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: AbstractPrefMeta#summary
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void setSummary_should_throw_if_summary_null() throws Exception {
        group.setSummary(null);
    }

    @Test
    public void summary_should_return_text() throws Exception {
        String summary = "My Summary";
        group.setSummary(summary);

        String result = group.getSummary();

        assertEquals(summary, result);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: AbstractPrefMeta#title
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void setTitle_should_throw_if_title_null() throws Exception {
        group.setTitle(null);
    }

    @Test
    public void title_should_return_text() throws Exception {
        String title = "My Title";
        group.setTitle(title);

        String result = group.getTitle();

        assertEquals(title, result);
    }

}

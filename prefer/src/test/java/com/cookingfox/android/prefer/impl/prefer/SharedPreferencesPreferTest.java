package com.cookingfox.android.prefer.impl.prefer;

import com.cookingfox.android.prefer.api.exception.GroupAlreadyAddedException;
import com.cookingfox.android.prefer.api.pref.PrefGroup;
import com.cookingfox.android.prefer.api.pref.PrefListener;
import com.cookingfox.android.prefer.api.pref.typed.BooleanPref;
import com.cookingfox.android.prefer.impl.pref.AndroidPrefGroup;
import com.cookingfox.android.prefer.impl.pref.typed.AndroidBooleanPref;
import com.cookingfox.android.prefer.impl.pref.typed.AndroidIntegerPref;
import com.cookingfox.android.prefer.impl.pref.typed.AndroidStringPref;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import fixtures.FixtureSharedPreferences;
import fixtures.example.Key;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link SharedPreferencesPrefer} and {@link AndroidPrefer}.
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
    // TESTS: getBoolean & putBoolean
    //----------------------------------------------------------------------------------------------

    @Test
    public void getBoolean_should_return_stored_value() throws Exception {
        boolean stored = false;

        prefer.putBoolean(Key.IsEnabled, stored);

        boolean result = prefer.getBoolean(Key.IsEnabled, true);

        assertEquals(stored, result);
    }

    @Test
    public void getBoolean_should_return_default_value_for_non_stored() throws Exception {
        boolean defaultValue = true;

        boolean result = prefer.getBoolean(Key.IsEnabled, defaultValue);

        assertEquals(defaultValue, result);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: getInteger & putInteger
    //----------------------------------------------------------------------------------------------

    @Test
    public void getInteger_should_return_stored_value() throws Exception {
        int stored = 1;

        prefer.putInteger(Key.IsEnabled, stored);

        int result = prefer.getInteger(Key.IsEnabled, 2);

        assertEquals(stored, result);
    }

    @Test
    public void getInteger_should_return_default_value_for_non_stored() throws Exception {
        int defaultValue = 1;

        int result = prefer.getInteger(Key.IsEnabled, defaultValue);

        assertEquals(defaultValue, result);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: getString & putString
    //----------------------------------------------------------------------------------------------

    @Test
    public void getString_should_return_stored_value() throws Exception {
        String stored = "foo";

        prefer.putString(Key.IsEnabled, stored);

        String result = prefer.getString(Key.IsEnabled, "bar");

        assertEquals(stored, result);
    }

    @Test
    public void getString_should_return_default_value_for_non_stored() throws Exception {
        String defaultValue = "foo";

        String result = prefer.getString(Key.IsEnabled, defaultValue);

        assertEquals(defaultValue, result);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: addGroup
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void addGroup_should_throw_if_group_null() throws Exception {
        prefer.addGroup(null);
    }

    @Test
    public void addGroup_should_create_and_add_new_pref_group() throws Exception {
        assertTrue(prefer.groups.size() == 0);

        AndroidPrefGroup<Key> group = new AndroidPrefGroup<>(prefer, Key.class);
        prefer.addGroup(group);

        assertTrue(prefer.groups.size() == 1);
        assertTrue(prefer.groups.containsValue(group));
    }

    @Test(expected = GroupAlreadyAddedException.class)
    public void addGroup_should_throw_if_already_contains_key_class() throws Exception {
        AndroidPrefGroup<Key> group = new AndroidPrefGroup<>(prefer, Key.class);
        prefer.addGroup(group);
        prefer.addGroup(group);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: addNewGroup
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void addNewGroup_should_throw_if_enum_class_null() throws Exception {
        prefer.addNewGroup(null);
    }

    @Test
    public void addNewGroup_should_create_and_add_new_pref_group() throws Exception {
        assertTrue(prefer.groups.size() == 0);

        AndroidPrefGroup<Key> group = prefer.addNewGroup(Key.class);

        assertTrue(prefer.groups.size() == 1);
        assertTrue(prefer.groups.containsValue(group));
    }

    @Test(expected = GroupAlreadyAddedException.class)
    public void addNewGroup_should_throw_if_already_contains_key_class() throws Exception {
        prefer.addNewGroup(Key.class);
        prefer.addNewGroup(Key.class);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: findGroup
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void findGroup_should_throw_if_key_class_null() throws Exception {
        prefer.findGroup(null);
    }

    @Test
    public void findGroup_should_not_throw_if_not_found() throws Exception {
        prefer.findGroup(Key.class);
    }

    @Test
    public void findGroup_should_return_group() throws Exception {
        AndroidPrefGroup<Key> group = prefer.addNewGroup(Key.class);

        PrefGroup<Key> result = prefer.findGroup(Key.class);

        assertSame(group, result);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: getGroups
    //----------------------------------------------------------------------------------------------

    @Test
    public void getGroups_should_return_empty_set_if_no_groups_added() throws Exception {
        Set<PrefGroup<? extends Enum>> groups = prefer.getGroups();

        assertNotNull(groups);
        assertTrue(groups.isEmpty());
    }

    @Test
    public void getGroups_should_return_groups() throws Exception {
        AndroidPrefGroup<Key> first = prefer.addNewGroup(Key.class);
        AndroidPrefGroup<Thread.State> second = prefer.addNewGroup(Thread.State.class);

        Set<PrefGroup<? extends Enum>> groups = prefer.getGroups();

        assertNotNull(groups);
        assertTrue(groups.contains(first));
        assertTrue(groups.contains(second));
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: newBoolean
    //----------------------------------------------------------------------------------------------

    @Test
    public void newBoolean_should_create_and_add_boolean_pref() throws Exception {
        AndroidBooleanPref<Key> pref = prefer.newBoolean(Key.IsEnabled, true);

        assertNotNull(pref);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: newInteger
    //----------------------------------------------------------------------------------------------

    @Test
    public void newInteger_should_create_and_add_integer_pref() throws Exception {
        AndroidIntegerPref<Key> pref = prefer.newInteger(Key.IntervalMs, 123);

        assertNotNull(pref);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: newString
    //----------------------------------------------------------------------------------------------

    @Test
    public void newString_should_create_and_add_string_pref() throws Exception {
        AndroidStringPref<Key> pref = prefer.newString(Key.Username, "foo");

        assertNotNull(pref);
    }

    //----------------------------------------------------------------------------------------------
    // HELPER METHODS
    //----------------------------------------------------------------------------------------------

    private BooleanPref<Key> createBooleanPref(boolean defaultValue) {
        return prefer.newBoolean(Key.IsEnabled, defaultValue);
    }

}

package com.cookingfox.android.prefer.impl.prefer;

import com.cookingfox.android.prefer.api.exception.GroupAlreadyAddedException;
import com.cookingfox.android.prefer.api.exception.PreferNotInitializedException;
import com.cookingfox.android.prefer.api.pref.OnGroupValueChanged;
import com.cookingfox.android.prefer.api.pref.OnValueChanged;
import com.cookingfox.android.prefer.api.pref.Pref;
import com.cookingfox.android.prefer.api.pref.PrefGroup;
import com.cookingfox.android.prefer.api.pref.typed.BooleanPref;
import com.cookingfox.android.prefer.impl.pref.AndroidPrefGroup;
import com.cookingfox.android.prefer.impl.pref.typed.AndroidBooleanPref;
import com.cookingfox.android.prefer.impl.pref.typed.AndroidIntegerPref;
import com.cookingfox.android.prefer.impl.pref.typed.AndroidStringPref;
import com.cookingfox.android.prefer_testing.fixtures.Key;
import com.cookingfox.android.prefer_testing.shared_preferences.InMemorySharedPreferences;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

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
        prefer = new SharedPreferencesPrefer(new InMemorySharedPreferences());
        prefer.initializePrefer();
    }

    @After
    public void tearDown() throws Exception {
        prefer.disposePrefer();
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: constructor
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void constructor_should_throw_if_preferences_null() throws Exception {
        new SharedPreferencesPrefer(null);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: addValueChangedListener
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void addValueChangedListener_should_throw_if_null_pref() throws Exception {
        // noinspection unchecked
        prefer.addValueChangedListener(null, new OnValueChanged<Boolean>() {
            @Override
            public void onValueChanged(Boolean value) {
                // ignored
            }
        });
    }

    @Test(expected = NullPointerException.class)
    public void addValueChangedListener_should_throw_if_null_listener() throws Exception {
        prefer.addValueChangedListener(createBooleanPref(true), null);
    }

    @Test
    public void addValueChangedListener_should_call_listener_on_value_changed() throws Exception {
        BooleanPref<Key> booleanPref = createBooleanPref(false);
        final AtomicBoolean called = new AtomicBoolean(false);

        prefer.addValueChangedListener(booleanPref, new OnValueChanged<Boolean>() {
            @Override
            public void onValueChanged(Boolean value) {
                called.set(true);
            }
        });

        booleanPref.setValue(true);

        assertTrue(called.get());
        assertTrue(booleanPref.getValue());
    }

    @Test(expected = PreferNotInitializedException.class)
    public void addValueChangedListener_should_throw_if_not_initialized() throws Exception {
        SharedPreferencesPrefer prefer = new SharedPreferencesPrefer(new InMemorySharedPreferences());
        AndroidBooleanPref<Key> pref = prefer.newBoolean(Key.IsEnabled, true);

        prefer.addValueChangedListener(pref, new OnValueChanged<Boolean>() {
            @Override
            public void onValueChanged(Boolean value) {
                // ignore
            }
        });
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: removeValueChangedListener
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void removeValueChangedListener_should_throw_if_null_prefer() throws Exception {
        // noinspection unchecked
        prefer.removeValueChangedListener(null, new OnValueChanged<Boolean>() {
            @Override
            public void onValueChanged(Boolean value) {
                // ignored
            }
        });
    }

    @Test(expected = NullPointerException.class)
    public void removeValueChangedListener_should_throw_if_null_listener() throws Exception {
        prefer.removeValueChangedListener(createBooleanPref(true), null);
    }

    @Test
    public void removeValueChangedListener_should_not_call_listener_on_value_changed() throws Exception {
        final AtomicBoolean called = new AtomicBoolean(false);

        BooleanPref<Key> booleanPref = createBooleanPref(false);
        OnValueChanged<Boolean> listener = new OnValueChanged<Boolean>() {
            @Override
            public void onValueChanged(Boolean value) {
                called.set(true);
            }
        };

        prefer.addValueChangedListener(booleanPref, listener);
        prefer.removeValueChangedListener(booleanPref, listener);

        booleanPref.setValue(true);

        assertFalse(called.get());
        assertTrue(booleanPref.getValue());
    }

    @Test(expected = PreferNotInitializedException.class)
    public void removeValueChangedListener_should_throw_if_not_initialized() throws Exception {
        SharedPreferencesPrefer prefer = new SharedPreferencesPrefer(new InMemorySharedPreferences());
        AndroidBooleanPref<Key> pref = prefer.newBoolean(Key.IsEnabled, true);

        prefer.removeValueChangedListener(pref, new OnValueChanged<Boolean>() {
            @Override
            public void onValueChanged(Boolean value) {
                // ignore
            }
        });
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: addGroupValueChangedListener
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void addGroupValueChangedListener_should_throw_if_group_null() throws Exception {
        prefer.addGroupValueChangedListener(null, new OnGroupValueChanged<Key>() {
            @Override
            public void onGroupValueChanged(Pref<Key, ?> pref) {
                // ignore
            }
        });
    }

    @Test(expected = NullPointerException.class)
    public void addGroupValueChangedListener_should_throw_if_listener_null() throws Exception {
        prefer.addGroupValueChangedListener(prefer.addNewGroup(Key.class), null);
    }

    @Test(expected = PreferNotInitializedException.class)
    public void addGroupValueChangedListener_should_throw_if_not_initialized() throws Exception {
        SharedPreferencesPrefer prefer = new SharedPreferencesPrefer(new InMemorySharedPreferences());

        prefer.addGroupValueChangedListener(prefer.addNewGroup(Key.class), new OnGroupValueChanged<Key>() {
            @Override
            public void onGroupValueChanged(Pref<Key, ?> pref) {
                // ignore
            }
        });
    }

    @Test
    public void addGroupValueChangedListener_should_call_listener_for_all_value_changed() throws Exception {
        final int intervalMsDefault = 1;
        final boolean isEnabledDefault = false;
        final String usernameDefault = "foo";

        final int intervalMsNew = 2;
        final boolean isEnabledNew = true;

        AndroidPrefGroup<Key> group = prefer.addNewGroup(Key.class);
        AndroidIntegerPref<Key> intervalMsPref = group.addNewInteger(Key.IntervalMs, intervalMsDefault);
        AndroidBooleanPref<Key> isEnabledPref = group.addNewBoolean(Key.IsEnabled, isEnabledDefault);
        AndroidStringPref<Key> usernamePref = group.addNewString(Key.Username, usernameDefault);

        final LinkedList<Key> groupValueChangedCalls = new LinkedList<>();
        final LinkedList<Key> expectedCalls = new LinkedList<>(Arrays.asList(Key.IntervalMs, Key.IsEnabled));

        group.addGroupValueChangedListener(new OnGroupValueChanged<Key>() {
            @Override
            public void onGroupValueChanged(Pref<Key, ?> pref) {
                groupValueChangedCalls.add(pref.getKey());
            }
        });

        intervalMsPref.setValue(intervalMsNew);
        isEnabledPref.setValue(isEnabledNew);

        assertEquals(intervalMsNew, (int) intervalMsPref.getValue());
        assertEquals(isEnabledNew, isEnabledPref.getValue());
        assertEquals(usernameDefault, usernamePref.getValue()); // should not have changed

        assertFalse(groupValueChangedCalls.isEmpty());
        assertEquals(expectedCalls, groupValueChangedCalls);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: removeGroupValueChangedListener
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void removeGroupValueChangedListener_should_throw_if_group_null() throws Exception {
        prefer.removeGroupValueChangedListener(null, new OnGroupValueChanged<Key>() {
            @Override
            public void onGroupValueChanged(Pref<Key, ?> pref) {
                // ignore
            }
        });
    }

    @Test(expected = NullPointerException.class)
    public void removeGroupValueChangedListener_should_throw_if_listener_null() throws Exception {
        prefer.removeGroupValueChangedListener(prefer.addNewGroup(Key.class), null);
    }

    @Test(expected = PreferNotInitializedException.class)
    public void removeGroupValueChangedListener_should_throw_if_not_initialized() throws Exception {
        SharedPreferencesPrefer prefer = new SharedPreferencesPrefer(new InMemorySharedPreferences());

        prefer.removeGroupValueChangedListener(prefer.addNewGroup(Key.class), new OnGroupValueChanged<Key>() {
            @Override
            public void onGroupValueChanged(Pref<Key, ?> pref) {
                // ignore
            }
        });
    }

    @Test
    public void removeGroupValueChangedListener_should_call_listener_for_all_value_changed() throws Exception {
        final int intervalMsDefault = 1;
        final boolean isEnabledDefault = false;
        final String usernameDefault = "foo";

        final int intervalMsNew = 2;
        final boolean isEnabledNew = true;

        AndroidPrefGroup<Key> group = prefer.addNewGroup(Key.class);
        AndroidIntegerPref<Key> intervalMsPref = group.addNewInteger(Key.IntervalMs, intervalMsDefault);
        AndroidBooleanPref<Key> isEnabledPref = group.addNewBoolean(Key.IsEnabled, isEnabledDefault);
        AndroidStringPref<Key> usernamePref = group.addNewString(Key.Username, usernameDefault);

        final LinkedList<Key> groupValueChangedCalls = new LinkedList<>();

        final OnGroupValueChanged<Key> listener = new OnGroupValueChanged<Key>() {
            @Override
            public void onGroupValueChanged(Pref<Key, ?> pref) {
                groupValueChangedCalls.add(pref.getKey());
            }
        };

        // first add, then remove
        group.addGroupValueChangedListener(listener);
        group.removeGroupValueChangedListener(listener);

        intervalMsPref.setValue(intervalMsNew);
        isEnabledPref.setValue(isEnabledNew);

        assertEquals(intervalMsNew, (int) intervalMsPref.getValue());
        assertEquals(isEnabledNew, isEnabledPref.getValue());
        assertEquals(usernameDefault, usernamePref.getValue()); // should not have changed

        assertTrue(groupValueChangedCalls.isEmpty());
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: disposePrefer
    //----------------------------------------------------------------------------------------------

    @Test
    public void disposePrefer_should_clear_all_pref_groups() throws Exception {
        AndroidPrefGroup<Key> group = prefer.addNewGroup(Key.class);

        assertTrue(prefer.groups.containsValue(group));

        prefer.disposePrefer();

        assertFalse(prefer.groups.containsValue(group));
    }

    @Test
    public void disposePrefer_should_clear_all_pref_listeners() throws Exception {
        AndroidBooleanPref<Key> pref = prefer.newBoolean(Key.IsEnabled, true);

        OnValueChanged<Boolean> listener = new OnValueChanged<Boolean>() {
            @Override
            public void onValueChanged(Boolean value) {
                // ignore
            }
        };

        prefer.addValueChangedListener(pref, listener);

        assertTrue(prefer.prefValueChangedListeners.containsKey(pref));

        prefer.disposePrefer();

        assertFalse(prefer.prefValueChangedListeners.containsKey(pref));
    }

    @Test
    public void disposePrefer_should_clear_all_group_listeners() throws Exception {
        AndroidPrefGroup<Key> group = prefer.addNewGroup(Key.class);

        OnGroupValueChanged<Key> listener = new OnGroupValueChanged<Key>() {
            @Override
            public void onGroupValueChanged(Pref<Key, ?> pref) {
                // ignore
            }
        };

        prefer.addGroupValueChangedListener(group, listener);

        assertTrue(prefer.prefGroupValueChangedListeners.containsKey(group));

        prefer.disposePrefer();

        assertFalse(prefer.prefGroupValueChangedListeners.containsKey(group));
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: getBoolean & putBoolean
    //----------------------------------------------------------------------------------------------

    @SuppressWarnings("all")
    @Test
    public void getBoolean_should_return_stored_value() throws Exception {
        boolean stored = false;

        prefer.putBoolean(Key.IsEnabled, stored);

        boolean result = prefer.getBoolean(Key.IsEnabled, true);

        assertEquals(stored, result);
    }

    @SuppressWarnings("all")
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
        // noinspection unchecked
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
        // noinspection unchecked
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
        // noinspection unchecked
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
    // TESTS: onChangeListener
    //----------------------------------------------------------------------------------------------

//    // NOTE: this test is for checking the error print only
//    @Test
//    public void onChangeListener_should_throw_if_key_cannot_be_deserialized() throws Exception {
//        prefer.onChangeListener.onSharedPreferenceChanged(null, "foo");
//    }

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

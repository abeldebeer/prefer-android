package com.cookingfox.android.prefer_fragment.impl;

import android.preference.Preference;

import com.cookingfox.android.prefer.api.prefer.Prefer;
import com.cookingfox.android.prefer.impl.pref.AndroidPrefGroup;
import com.cookingfox.android.prefer.impl.pref.typed.AndroidStringPref;
import com.cookingfox.android.prefer.impl.prefer.AndroidPrefer;
import com.cookingfox.android.prefer.impl.prefer.SharedPreferencesPrefer;
import com.cookingfox.android.prefer_testing.fixtures.ExamplePrefs.ExampleKey;
import com.cookingfox.android.prefer_testing.fixtures.Key;
import com.cookingfox.android.prefer_testing.shared_preferences.InMemorySharedPreferences;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for {@link PreferFragment}.
 */
public class PreferFragmentTest {

    //----------------------------------------------------------------------------------------------
    // TEST SETUP
    //----------------------------------------------------------------------------------------------

    private Prefer prefer;

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
    // TESTS: create
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void create_should_throw_if_prefer_null() throws Exception {
        PreferFragment.create(null);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: populatePreferenceWithMeta with enum Key
    //----------------------------------------------------------------------------------------------

    @Test
    public void populatePreferenceWithMeta_should_set_correct_default_title_for_preference_with_key() throws Exception {
        PreferFragment fragment = new PreferFragment();
        Preference mockPreference = mock(Preference.class);
        AndroidStringPref<Key> pref = new AndroidStringPref<>(prefer, Key.Username, "");

        fragment.populatePreferenceWithMeta(mockPreference, pref);
        verify(mockPreference).setTitle("Username");
    }

    @Test
    public void populatePreferenceWithMeta_should_set_correct_custom_title_for_preference_with_key() throws Exception {
        PreferFragment fragment = new PreferFragment();
        Preference mockPreference = mock(Preference.class);
        mockPreference.setTitle("Joep Slenter");
        AndroidStringPref<Key> pref = new AndroidStringPref<>(prefer, Key.Username, "");

        fragment.populatePreferenceWithMeta(mockPreference, pref);
        verify(mockPreference).setTitle("Joep Slenter");
    }

    @Test
    public void populatePreferenceWithMeta_should_set_correct_default_title_for_preference_group_with_key() throws Exception {
        AndroidPrefer androidPrefer = mock(AndroidPrefer.class);
        PreferFragment fragment = new PreferFragment();
        Preference mockPreference = mock(Preference.class);
        AndroidPrefGroup<Key> group = new AndroidPrefGroup<>(androidPrefer, Key.class);

        fragment.populatePreferenceWithMeta(mockPreference, group);
        verify(mockPreference).setTitle("Key");
    }

    @Test
    public void populatePreferenceWithMeta_should_set_correct_custom_title_for_preference_group_with_key() throws Exception {
        AndroidPrefer androidPrefer = mock(AndroidPrefer.class);
        PreferFragment fragment = new PreferFragment();
        Preference mockPreference = mock(Preference.class);
        AndroidPrefGroup<Key> group = new AndroidPrefGroup<>(androidPrefer, Key.class);
        group.setTitle("Joep Slenter");

        fragment.populatePreferenceWithMeta(mockPreference, group);
        verify(mockPreference).setTitle("Joep Slenter");
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: populatePreferenceWithMeta with enum ExampleKey
    //----------------------------------------------------------------------------------------------

    @Test
    public void populatePreferenceWithMeta_should_set_correct_default_title_for_preference_with_example_key() throws Exception {
        PreferFragment fragment = new PreferFragment();
        Preference mockPreference = mock(Preference.class);
        AndroidStringPref<ExampleKey> pref = new AndroidStringPref<>(prefer, ExampleKey.ExampleTitle, "");

        fragment.populatePreferenceWithMeta(mockPreference, pref);
        verify(mockPreference).setTitle("ExampleTitle");
    }

    @Test
    public void populatePreferenceWithMeta_should_set_correct_custom_title_for_preference_example_key() throws Exception {
        PreferFragment fragment = new PreferFragment();
        Preference mockPreference = mock(Preference.class);
        mockPreference.setTitle("Joep Slenter");
        AndroidStringPref<ExampleKey> pref = new AndroidStringPref<>(prefer, ExampleKey.ExampleTitle, "");

        fragment.populatePreferenceWithMeta(mockPreference, pref);
        verify(mockPreference).setTitle("Joep Slenter");
    }

    @Test
    public void populatePreferenceWithMeta_should_set_correct_default_title_for_preference_group_example_key() throws Exception {
        AndroidPrefer androidPrefer = mock(AndroidPrefer.class);
        PreferFragment fragment = new PreferFragment();
        Preference mockPreference = mock(Preference.class);
        AndroidPrefGroup<ExampleKey> group = new AndroidPrefGroup<>(androidPrefer, ExampleKey.class);

        fragment.populatePreferenceWithMeta(mockPreference, group);
        verify(mockPreference).setTitle("ExamplePrefs$ExampleKey");
    }

    @Test
    public void populatePreferenceWithMeta_should_set_correct_custom_title_for_preference_group_example_key() throws Exception {
        AndroidPrefer androidPrefer = mock(AndroidPrefer.class);
        PreferFragment fragment = new PreferFragment();
        Preference mockPreference = mock(Preference.class);
        AndroidPrefGroup<ExampleKey> group = new AndroidPrefGroup<>(androidPrefer, ExampleKey.class);
        group.setTitle("Joep Slenter");

        fragment.populatePreferenceWithMeta(mockPreference, group);
        verify(mockPreference).setTitle("Joep Slenter");
    }
}

package com.cookingfox.android.prefer_fragment.impl;

import com.cookingfox.android.prefer.api.prefer.Prefer;
import com.cookingfox.android.prefer.impl.prefer.SharedPreferencesPrefer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fixtures.FixtureSharedPreferences;

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
        prefer = new SharedPreferencesPrefer(new FixtureSharedPreferences());
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

}

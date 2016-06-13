package com.cookingfox.android.prefer.impl.prefer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fixtures.FixtureSharedPreferences;

/**
 * Unit tests for {@link AndroidPreferProvider}.
 */
public class AndroidPreferProviderTest {

    //----------------------------------------------------------------------------------------------
    // TEST SETUP
    //----------------------------------------------------------------------------------------------

    private AndroidPrefer prefer;

    @Before
    public void setUp() throws Exception {
        prefer = new SharedPreferencesPrefer(new FixtureSharedPreferences());
    }

    @After
    public void tearDown() throws Exception {
        AndroidPreferProvider.defaultInstance = null;
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: disposeDefault
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void disposeDefault_should_throw_if_instance_null() throws Exception {
        AndroidPreferProvider.disposeDefault();
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: getDefault
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void getDefault_should_throw_if_context_null() throws Exception {
        AndroidPreferProvider.getDefault(null);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: setDefault
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void setDefault_should_throw_if_instance_null() throws Exception {
        AndroidPreferProvider.setDefault(null);
    }

    @Test(expected = IllegalStateException.class)
    public void setDefault_should_throw_if_already_set() throws Exception {
        AndroidPreferProvider.setDefault(prefer);
        AndroidPreferProvider.setDefault(prefer);
    }

}

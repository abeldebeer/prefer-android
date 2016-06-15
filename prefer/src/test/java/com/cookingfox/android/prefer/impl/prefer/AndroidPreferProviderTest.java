package com.cookingfox.android.prefer.impl.prefer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cookingfox.android.prefer_testing.shared_preferences.InMemorySharedPreferences;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        prefer = new SharedPreferencesPrefer(new InMemorySharedPreferences());
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

    @Test
    public void setDefault_should_initialize_if_not_initialized() throws Exception {
        assertFalse(prefer.initialized);

        AndroidPreferProvider.setDefault(prefer);

        assertTrue(prefer.initialized);
    }

}

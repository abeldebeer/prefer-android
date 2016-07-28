package com.cookingfox.android.prefer.impl.prefer;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

import com.cookingfox.android.prefer_testing.shared_preferences.InMemorySharedPreferences;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link AndroidPrefer}.
 */
public class AndroidPreferTest {

    //----------------------------------------------------------------------------------------------
    // TEST SETUP
    //----------------------------------------------------------------------------------------------

    private MockAndroidPrefer prefer;

    @Before
    public void setUp() throws Exception {
        prefer = new MockAndroidPrefer(new InMemorySharedPreferences());
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: initializePrefer
    //----------------------------------------------------------------------------------------------

    @Test
    public void initializePrefer_should_not_initialize_twice_when_called_twice() throws Exception {
        prefer.initializePrefer();
        prefer.initializePrefer();

        assertEquals(1, prefer.numInitializePreferCalls);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: disposePrefer
    //----------------------------------------------------------------------------------------------

    @Test
    public void disposePrefer_should_not_dispose_twice_when_called_twice() throws Exception {
        // needs to initialize first
        prefer.initializePrefer();

        prefer.disposePrefer();
        prefer.disposePrefer();

        assertEquals(1, prefer.numDisposePreferCalls);
    }

    //----------------------------------------------------------------------------------------------
    // HELPER CLASSES
    //----------------------------------------------------------------------------------------------

    static class MockAndroidPrefer extends AndroidPrefer {
        final SharedPreferences sharedPreferences;
        public int numInitializePreferCalls = 0;
        public int numDisposePreferCalls = 0;

        public MockAndroidPrefer(SharedPreferences sharedPreferences) {
            this.sharedPreferences = sharedPreferences;
        }

        @Override
        protected PreferHelper createHelper() {
            return new SharedPreferencesHelper(sharedPreferences, listener) {
                @Override
                public void initializePrefer() {
                    numInitializePreferCalls++;
                    super.initializePrefer();
                }

                @Override
                public void disposePrefer() {
                    numDisposePreferCalls++;
                    super.disposePrefer();
                }
            };
        }

        final OnSharedPreferenceChangeListener listener = new OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                throw new RuntimeException("Listener not expected to be called during tests");
            }
        };
    }

}

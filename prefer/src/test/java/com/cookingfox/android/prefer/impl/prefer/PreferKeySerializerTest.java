package com.cookingfox.android.prefer.impl.prefer;

import org.junit.Test;

import com.cookingfox.android.prefer_testing.fixtures.Key;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link PreferKeySerializer}.
 */
public class PreferKeySerializerTest {

    //----------------------------------------------------------------------------------------------
    // TESTS: deserializeKey
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void deserializeKey_should_throw_if_string_null() throws Exception {
        PreferKeySerializer.deserializeKey(null);
    }

    @Test(expected = ClassNotFoundException.class)
    public void deserializeKey_should_throw_if_string_empty() throws Exception {
        PreferKeySerializer.deserializeKey("");
    }

    @Test(expected = ClassNotFoundException.class)
    public void deserializeKey_should_throw_if_enum_not_exists() throws Exception {
        PreferKeySerializer.deserializeKey("foo bar");
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: serializeKey
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void serializeKey_should_throw_if_enum_null() throws Exception {
        PreferKeySerializer.serializeKey(null);
    }

    @Test
    public void serializeKey_should_return_serialized_key_one() throws Exception {
        String serialized = PreferKeySerializer.serializeKey(Key.Username);
        String expected = Key.Username.getClass().getName() +
                PreferKeySerializer.ENUM_KEY_SEPARATOR +
                Key.Username.name();

        assertEquals(expected, serialized);
    }

    @Test
    public void serializeKey_should_return_serialized_key_two() throws Exception {
        String serialized = PreferKeySerializer.serializeKey(Key.IsEnabled);
        String expected = Key.IsEnabled.getClass().getName() +
                PreferKeySerializer.ENUM_KEY_SEPARATOR +
                Key.IsEnabled.name();

        assertEquals(expected, serialized);
    }

}

package com.cookingfox.android.prefer.impl.prefer;

/**
 * Provides (de-)serialization functionality for enum keys.
 */
public class PreferKeySerializer {

    /**
     * Serialization separator character.
     */
    protected static final String ENUM_KEY_SEPARATOR = "-";

    /**
     * De-serializes a string into its original enum value.
     *
     * @param serialized The serialized enum key.
     * @return The de-serialized enum.
     * @throws ClassNotFoundException when the enum class could not be found.
     */
    public static Enum deserializeKey(String serialized) throws ClassNotFoundException {
        String[] split = serialized.split(ENUM_KEY_SEPARATOR);

        return Enum.valueOf((Class) Class.forName(split[0]), split[1]);
    }

    /**
     * Returns a serialized string representation of the enum key.
     *
     * @param key The enum value.
     * @return Serialized string.
     */
    public static String serializeKey(Enum key) {
        return key.getClass().getName() + ENUM_KEY_SEPARATOR + key.name();
    }

}

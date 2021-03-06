package com.cookingfox.android.prefer.impl.prefer;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.cookingfox.android.prefer.api.prefer.Prefer;

import static com.cookingfox.guava_preconditions.Preconditions.checkNotNull;
import static com.cookingfox.guava_preconditions.Preconditions.checkState;

/**
 * Provides static access to the default Prefer instance.
 */
public final class AndroidPreferProvider {

    /**
     * Default Prefer instance.
     */
    protected static AndroidPrefer defaultInstance;

    /**
     * Not supposed to be instantiated.
     */
    private AndroidPreferProvider() {
    }

    /**
     * Dispose the default Prefer instance.
     *
     * @see Prefer#disposePrefer()
     */
    public static void disposeDefault() {
        checkNotNull(defaultInstance, "The default instance is not set").disposePrefer();

        // unset default instance
        defaultInstance = null;
    }

    /**
     * Returns the default Prefer instance. If it has not been set using
     * {@link #setDefault(AndroidPrefer)}, a default instance is created using
     * {@link PreferenceManager#getDefaultSharedPreferences(Context)}.
     *
     * @param context The context from which to get the default shared preferences.
     * @return The default Prefer instance.
     */
    public static AndroidPrefer getDefault(Context context) {
        checkNotNull(context, "Context can not be null");

        // default instance not set yet: create using context
        if (defaultInstance == null) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

            defaultInstance = new SharedPreferencesPrefer(preferences);
            defaultInstance.initializePrefer();
        }

        return defaultInstance;
    }

    /**
     * Set the default Prefer instance.
     *
     * @param prefer The Prefer instance to set as default.
     * @throws IllegalStateException when the default instance was already set.
     */
    public static void setDefault(AndroidPrefer prefer) {
        checkState(defaultInstance == null, "Default instance was already set");

        AndroidPreferProvider.defaultInstance =
                checkNotNull(prefer, "Default instance can not be null");

        // initialize if not initialized
        if (!prefer.initialized) {
            prefer.initializePrefer();
        }
    }

}

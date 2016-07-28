package com.cookingfox.android.prefer.api.prefer;

/**
 * Prefer lifecycle methods.
 */
public interface PreferLifecycle {

    /**
     * Initialize the Prefer library.
     */
    void initializePrefer();

    /**
     * Dispose the Prefer library. Clears all added groups and listeners.
     */
    void disposePrefer();

}

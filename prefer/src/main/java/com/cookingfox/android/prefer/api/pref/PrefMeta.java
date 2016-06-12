package com.cookingfox.android.prefer.api.pref;

/**
 * Meta data for a Pref / Pref group.
 */
public interface PrefMeta {

    /**
     * Returns whether this Pref is enabled.
     *
     * @return Whether this Pref is enabled.
     */
    boolean enable();

    /**
     * Returns the Pref summary (description of its use).
     *
     * @return The Pref summary.
     */
    String getSummary();

    /**
     * Returns the Pref title.
     *
     * @return The Pref title.
     */
    String getTitle();

    /**
     * Returns whether this Pref should be visible.
     *
     * @return Whether this Pref should be visible.
     */
    boolean show();

}

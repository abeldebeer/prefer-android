package com.cookingfox.android.prefer.api.pref;

/**
 * Listener interface for when the value changes of one of a {@link PrefGroup}'s {@link Pref}s.
 *
 * @param <K> References the enum class for this Pref's key.
 */
public interface OnGroupValueChanged<K extends Enum<K>> {

    /**
     * Called when the value changes of one of the group's Prefs.
     *
     * @param pref The Pref whose value changed.
     */
    void onGroupValueChanged(Pref<K, ?> pref);

}

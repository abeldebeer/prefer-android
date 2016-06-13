package com.cookingfox.android.prefer.impl.pref;

import android.preference.Preference;

/**
 * Allows a Pref to modify its generated {@link Preference}.
 */
public interface PreferenceModifier {

    /**
     * Modify the generated preference.
     *
     * @param generated The generated preference.
     * @return The modified preference.
     */
    Preference modifyPreference(Preference generated);

}

package com.cookingfox.android.prefer.impl.pref;

import android.preference.Preference;

/**
 * Created by abeldebeer on 10/05/16.
 */
public interface PreferenceModifier {
    Preference modifyPreference(Preference generated);
}

package com.cookingfox.android.prefer.api.pref;

/**
 * Created by abeldebeer on 11/06/16.
 */
public interface Prefs<K extends Enum<K>> {

    PrefGroup<K> getPrefGroup();

}

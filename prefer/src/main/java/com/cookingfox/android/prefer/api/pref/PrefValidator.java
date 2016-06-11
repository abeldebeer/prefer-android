package com.cookingfox.android.prefer.api.pref;

/**
 * Created by abeldebeer on 10/05/16.
 */
public interface PrefValidator<V> {
    boolean validate(V value) throws Exception;
}

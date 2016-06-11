package com.cookingfox.android.prefer.api.prefer.typed;

import com.cookingfox.android.prefer.api.prefer.Prefer;

/**
 * Created by abeldebeer on 10/05/16.
 */
public interface StringPrefer {
    String getString(Enum key, String defaultValue);

    Prefer putString(Enum key, String value);
}

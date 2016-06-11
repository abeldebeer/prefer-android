package com.cookingfox.android.prefer.api.prefer.typed;

import com.cookingfox.android.prefer.api.prefer.Prefer;

/**
 * Created by abeldebeer on 10/05/16.
 */
public interface BooleanPrefer {
    Boolean getBoolean(Enum key, Boolean defaultValue);

    Prefer putBoolean(Enum key, Boolean value);
}

package com.cookingfox.android.prefer.api.prefer.typed;

import com.cookingfox.android.prefer.api.prefer.Prefer;

/**
 * Created by abeldebeer on 10/05/16.
 */
public interface IntegerPrefer {
    Integer getInteger(Enum key, Integer defaultValue);

    Prefer putInteger(Enum key, Integer value);
}

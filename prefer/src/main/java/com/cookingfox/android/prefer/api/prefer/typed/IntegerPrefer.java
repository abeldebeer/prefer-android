package com.cookingfox.android.prefer.api.prefer.typed;

import com.cookingfox.android.prefer.api.prefer.Prefer;

/**
 * Created by abeldebeer on 10/05/16.
 */
public interface IntegerPrefer {
    int getInteger(Enum key, int defaultValue);

    Prefer putInteger(Enum key, int value);
}

package com.cookingfox.android.prefer.api.prefer;

import com.cookingfox.android.prefer.api.prefer.typed.BooleanPrefer;
import com.cookingfox.android.prefer.api.prefer.typed.FloatPrefer;
import com.cookingfox.android.prefer.api.prefer.typed.IntegerPrefer;
import com.cookingfox.android.prefer.api.prefer.typed.LongPrefer;
import com.cookingfox.android.prefer.api.prefer.typed.StringPrefer;

/**
 * Wrapper interface for all typed preference operations.
 */
public interface TypedPrefer extends
        BooleanPrefer,
        FloatPrefer,
        IntegerPrefer,
        LongPrefer,
        StringPrefer {
}

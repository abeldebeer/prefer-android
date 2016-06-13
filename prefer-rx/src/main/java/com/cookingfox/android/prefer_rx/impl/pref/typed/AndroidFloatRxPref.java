package com.cookingfox.android.prefer_rx.impl.pref.typed;

import com.cookingfox.android.prefer.impl.pref.typed.AndroidFloatPref;
import com.cookingfox.android.prefer_rx.api.pref.RxPref;
import com.cookingfox.android.prefer_rx.api.pref.typed.FloatRxPref;
import com.cookingfox.android.prefer_rx.api.prefer.RxPrefer;

import rx.Observable;

/**
 * {@link RxPref} implementation with a float value.
 *
 * @param <K> References the enum class for this Pref's key.
 */
public class AndroidFloatRxPref<K extends Enum<K>>
        extends AndroidFloatPref<K>
        implements FloatRxPref<K> {

    protected final RxPrefer rxPrefer;

    public AndroidFloatRxPref(RxPrefer prefer, K key, float defaultValue) {
        super(prefer, key, defaultValue);

        rxPrefer = prefer;
    }

    @Override
    public Observable<Float> observe() {
        return rxPrefer.observePref(this);
    }

}

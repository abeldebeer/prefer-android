package com.cookingfox.android.prefer_rx.impl.pref.typed;

import com.cookingfox.android.prefer.impl.pref.typed.AndroidBooleanPref;
import com.cookingfox.android.prefer_rx.api.pref.RxPref;
import com.cookingfox.android.prefer_rx.api.pref.typed.BooleanRxPref;
import com.cookingfox.android.prefer_rx.api.prefer.RxPrefer;

import rx.Observable;

/**
 * {@link RxPref} implementation with a boolean value.
 *
 * @param <K> References the enum class for this Pref's key.
 */
public class AndroidBooleanRxPref<K extends Enum<K>>
        extends AndroidBooleanPref<K>
        implements BooleanRxPref<K> {

    protected final RxPrefer rxPrefer;

    public AndroidBooleanRxPref(RxPrefer prefer, K key, boolean defaultValue) {
        super(prefer, key, defaultValue);

        rxPrefer = prefer;
    }

    @Override
    public Observable<Boolean> observe() {
        return rxPrefer.observePref(this);
    }

}

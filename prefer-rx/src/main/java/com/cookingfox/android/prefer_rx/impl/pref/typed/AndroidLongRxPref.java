package com.cookingfox.android.prefer_rx.impl.pref.typed;

import com.cookingfox.android.prefer.impl.pref.typed.AndroidLongPref;
import com.cookingfox.android.prefer_rx.api.pref.RxPref;
import com.cookingfox.android.prefer_rx.api.pref.typed.LongRxPref;
import com.cookingfox.android.prefer_rx.api.prefer.RxPrefer;

import rx.Observable;

/**
 * {@link RxPref} implementation with a long value.
 *
 * @param <K> References the enum class for this Pref's key.
 */
public class AndroidLongRxPref<K extends Enum<K>>
        extends AndroidLongPref<K>
        implements LongRxPref<K> {

    protected final RxPrefer rxPrefer;

    public AndroidLongRxPref(RxPrefer prefer, K key, long defaultValue) {
        super(prefer, key, defaultValue);

        rxPrefer = prefer;
    }

    @Override
    public Observable<Long> observe() {
        return rxPrefer.observePref(this);
    }

}

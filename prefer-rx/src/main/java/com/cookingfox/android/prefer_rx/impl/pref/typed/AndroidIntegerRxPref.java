package com.cookingfox.android.prefer_rx.impl.pref.typed;

import com.cookingfox.android.prefer.impl.pref.typed.AndroidIntegerPref;
import com.cookingfox.android.prefer_rx.api.pref.RxPref;
import com.cookingfox.android.prefer_rx.api.pref.typed.IntegerRxPref;
import com.cookingfox.android.prefer_rx.api.prefer.RxPrefer;

import rx.Observable;

/**
 * {@link RxPref} implementation with an integer value.
 *
 * @param <K> References the enum class for this Pref's key.
 */
public class AndroidIntegerRxPref<K extends Enum<K>>
        extends AndroidIntegerPref<K>
        implements IntegerRxPref<K> {

    protected final RxPrefer rxPrefer;

    public AndroidIntegerRxPref(RxPrefer prefer, K key, int defaultValue) {
        super(prefer, key, defaultValue);

        rxPrefer = prefer;
    }

    @Override
    public Observable<Integer> observeValueChanges() {
        return rxPrefer.observeValueChanges(this);
    }

}

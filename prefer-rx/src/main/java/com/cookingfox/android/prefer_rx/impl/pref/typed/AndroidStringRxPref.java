package com.cookingfox.android.prefer_rx.impl.pref.typed;

import com.cookingfox.android.prefer.impl.pref.typed.AndroidStringPref;
import com.cookingfox.android.prefer_rx.api.pref.RxPref;
import com.cookingfox.android.prefer_rx.api.pref.typed.StringRxPref;
import com.cookingfox.android.prefer_rx.api.prefer.RxPrefer;

import rx.Observable;

/**
 * {@link RxPref} implementation with a String value.
 *
 * @param <K> References the enum class for this Pref's key.
 */
public class AndroidStringRxPref<K extends Enum<K>>
        extends AndroidStringPref<K>
        implements StringRxPref<K> {

    protected final RxPrefer rxPrefer;

    public AndroidStringRxPref(RxPrefer prefer, K key, String defaultValue) {
        super(prefer, key, defaultValue);

        rxPrefer = prefer;
    }

    @Override
    public Observable<String> observe() {
        return rxPrefer.observePref(this);
    }

}

package com.cookingfox.android.prefer.api.prefer;

import com.cookingfox.android.prefer.api.pref.Pref;
import com.cookingfox.android.prefer.api.pref.PrefGroup;
import com.cookingfox.android.prefer.api.pref.PrefListener;
import com.cookingfox.android.prefer.api.prefer.typed.BooleanPrefer;
import com.cookingfox.android.prefer.api.prefer.typed.IntegerPrefer;
import com.cookingfox.android.prefer.api.prefer.typed.StringPrefer;

import java.util.Set;

/**
 * Created by abeldebeer on 10/05/16.
 */
public interface Prefer extends BooleanPrefer, IntegerPrefer, StringPrefer {

    void disposePrefer();

    void initializePrefer();

    <K extends Enum<K>> PrefGroup<K> findGroup(Class<K> keyClass);

    Set<PrefGroup<? extends Enum>> getGroups();

    <K extends Enum<K>, V> Prefer addListener(Pref<K, V> pref, PrefListener<V> listener);

    <K extends Enum<K>, V> Prefer removeListener(Pref<K, V> pref, PrefListener<V> listener);

//    <K extends Enum<K>, V> Observable<V> observePrefValueChanges(Pref<K, V> pref);

}

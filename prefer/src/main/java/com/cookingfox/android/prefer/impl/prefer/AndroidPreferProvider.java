package com.cookingfox.android.prefer.impl.prefer;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by abeldebeer on 30/05/16.
 */
public class AndroidPreferProvider {

    private static volatile AndroidPrefer defaultInstance;

    /**
     * Not supposed to be initialized.
     */
    private AndroidPreferProvider() {
    }

    public static void disposeDefault() {
        defaultInstance.disposePrefer();
    }

    public static AndroidPrefer getDefault(Context context) {
        if (defaultInstance == null) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

            defaultInstance = new SharedPreferencesPrefer(preferences);
            defaultInstance.initializePrefer();
        }

        return defaultInstance;
    }

    public static void setDefault(AndroidPrefer prefer) {
        AndroidPreferProvider.defaultInstance = prefer;
    }

}

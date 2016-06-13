package com.cookingfox.android.prefer.sample;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.cookingfox.android.prefer.impl.prefer.AndroidPrefer;
import com.cookingfox.android.prefer.impl.prefer.AndroidPreferProvider;
import com.cookingfox.android.prefer_fragment.impl.PreferFragment;

/**
 * Example preference activity that creates a Prefer fragment.
 */
public class PreferActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidPrefer prefer = AndroidPreferProvider.getDefault(this);
        PreferFragment preferFragment = PreferFragment.create(prefer);

        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, preferFragment)
                .commit();
    }

}

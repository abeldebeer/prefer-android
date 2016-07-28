package com.cookingfox.android.prefer.sample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cookingfox.android.prefer.api.pref.OnValueChanged;
import com.cookingfox.android.prefer.impl.prefer.AndroidPreferProvider;
import com.cookingfox.android.prefer.sample.prefs.AndroidRestApiPrefs;
import com.cookingfox.android.prefer.sample.prefs.RestApiPrefs;
import com.cookingfox.android.prefer_rx.api.pref.typed.BooleanRxPref;
import com.cookingfox.android.prefer_rx.impl.prefer.SharedPreferencesRxPrefer;

import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSettings();
            }
        });

        // initialize prefer
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferencesRxPrefer prefer = new SharedPreferencesRxPrefer(preferences);

        // set default prefer instance on provider
        AndroidPreferProvider.setDefault(prefer);

        // create prefs
        RestApiPrefs restApiPrefs = new AndroidRestApiPrefs(prefer);
        BooleanRxPref<RestApiPrefs.Key> cacheEnabled = restApiPrefs.cacheEnabled();

        // add value changed listener
        cacheEnabled.addValueChangedListener(new OnValueChanged<Boolean>() {
            @Override
            public void onValueChanged(Boolean value) {
                Log.i(TAG, "[Listener] `cacheEnabled` value changed: " + value);
            }
        });

        // observe with rx
        cacheEnabled.observeValueChanges().subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean value) {
                Log.i(TAG, "[Rx Observable] `cacheEnabled` value changed: " + value);
            }
        });

        // toggle cache enabled preference
        cacheEnabled.setValue(!cacheEnabled.getValue());
    }

    private void showSettings() {
        startActivity(new Intent(this, PreferActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showSettings();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

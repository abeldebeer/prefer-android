package com.cookingfox.android.prefer.sample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cookingfox.android.prefer.impl.prefer.AndroidPreferProvider;
import com.cookingfox.android.prefer.sample.prefs.AndroidRestApiPrefs;
import com.cookingfox.android.prefer.sample.prefs.RestApiPrefs;
import com.cookingfox.android.prefer_rx.impl.prefer.SharedPreferencesRxPrefer;

public class MainActivity extends AppCompatActivity {

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

        // set default prefer
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferencesRxPrefer prefer = new SharedPreferencesRxPrefer(preferences);
        AndroidPreferProvider.setDefault(prefer);

        RestApiPrefs restApiPrefs = new AndroidRestApiPrefs(prefer);
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

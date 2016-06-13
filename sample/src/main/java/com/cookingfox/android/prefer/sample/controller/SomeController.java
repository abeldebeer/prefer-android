package com.cookingfox.android.prefer.sample.controller;

import com.cookingfox.android.prefer.sample.prefs.RestApiPrefs;

import rx.functions.Action1;

/**
 * Example controller that uses the REST API prefs.
 */
public class SomeController {

    private final RestApiPrefs prefs;

    public SomeController(RestApiPrefs prefs) {
        this.prefs = prefs;
    }

    public void example() {
        prefs.cacheEnabled().observe().subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                // do something with change
            }
        });
    }

}

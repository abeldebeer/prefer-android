# Prefer for Android

Helper library to make usage of Android SharedPreferences more powerful.

[![Build Status](https://travis-ci.org/cookingfox/prefer-android.svg?branch=master)](https://travis-ci.org/cookingfox/prefer-android)
[![](https://jitpack.io/v/cookingfox/prefer-android.svg)](https://jitpack.io/#cookingfox/prefer-android)

## Installation

This library uses [JitPack](https://jitpack.io/#cookingfox/prefer-android) for distribution.

Add the JitPack repository to your Android project's ROOT `build.gradle` file:

```groovy
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

Add the desired dependencies to your app's `build.gradle` file:

```groovy
dependencies {
    // core library
    compile 'com.github.cookingfox.prefer-android:prefer:0.1.0'
    
    // rx extension
    compile 'com.github.cookingfox.prefer-android:prefer-rx:0.1.0'
    
    // generate a preference fragment
    compile 'com.github.cookingfox.prefer-android:prefer-fragment:0.1.0'
}
```

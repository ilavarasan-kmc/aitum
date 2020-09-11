package com.urufit.aitum.ui;

import android.app.Application;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "DEFAULT", "fonts/balsamiqsansregular.ttf");
    }
}

package com.vilyever.androidlogger;

import android.app.Application;

import com.vilyever.logger.LoggerDisplay;

/**
 * App
 * AndroidLogger <com.vilyever.androidlogger>
 * Created by vilyever on 2016/3/23.
 * Feature:
 */
public class App extends Application {
    final App self = this;

    
    /* Constructors */
    
    
    /* Public Methods */
    
    
    /* Properties */
    
    
    /* Overrides */

    @Override
    public void onCreate() {
        super.onCreate();

        LoggerDisplay.initialize(this);
    }

    /* Delegates */
     
     
    /* Private Methods */
    
}
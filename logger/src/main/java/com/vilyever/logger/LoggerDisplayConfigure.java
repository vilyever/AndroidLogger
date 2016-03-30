package com.vilyever.logger;

import android.graphics.Color;

/**
 * LoggerDisplayConfigure
 * AndroidLogger <com.vilyever.logger>
 * Created by vilyever on 2016/3/30.
 * Feature:
 */
public class LoggerDisplayConfigure {
    final LoggerDisplayConfigure self = this;

    public static int DebugBackgroundColor = Color.rgb(255, 250, 152);
    public static int DebugTextColor = Color.rgb(30, 144, 255);
    public static int InfoBackgroundColor = Color.WHITE;
    public static int InfoTextColor = Color.BLACK;
    public static int WarnBackgroundColor = Color.DKGRAY;
    public static int WarnTextColor = Color.YELLOW;
    public static int ErrorBackgroundColor = Color.WHITE;
    public static int ErrorTextColor = Color.RED;
    public static int AssertBackgroundColor = Color.WHITE;
    public static int AssertTextColor = Color.rgb(127, 0, 0);

    /* Constructors */
    
    
    /* Public Methods */
    public static void notifyConfigureChanged() {
        LoggerDisplay.notifyLogChanged();
    }
    
    /* Properties */
    
    
    /* Overrides */
     
     
    /* Delegates */
     
     
    /* Private Methods */
    
}
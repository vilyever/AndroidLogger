package com.vilyever.logger;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.HashMap;

/**
 * LoggerDisplay
 * AndroidLogger <com.vilyever.logger>
 * Created by vilyever on 2016/3/29.
 * Feature:
 */
public class LoggerDisplay implements Application.ActivityLifecycleCallbacks {
    final LoggerDisplay self = this;

    
    /* Constructors */
    private static LoggerDisplay instance;
    public static synchronized LoggerDisplay getInstance() {
        if (instance == null) {
            instance = new LoggerDisplay();
        }
        return instance;
    }

    private void init(Application application) {
        this.application = application;

        this.application.registerActivityLifecycleCallbacks(this);
    }
    
    
    /* Public Methods */
    public static synchronized void initialize(Application application) {
        getInstance().init(application);
    }

    public static synchronized void notifyLogChanged() {
        for (LoggerDisplayView loggerDisplayView : getInstance().getLoggerDisplayViewHashMap().values()) {
            loggerDisplayView.notifyLogChanged();
        }
    }

    public static void setDisplayLogTag(String tag) {
        getInstance().setDisplayTag(tag);
    }
    
    /* Properties */
    private Application application;
    protected Application getApplication() {
        return this.application;
    }

    private Activity resumedActivity;
    protected LoggerDisplay setResumedActivity(Activity resumedActivity) {
        this.resumedActivity = resumedActivity;

        if (Logger.isDebugging()) {
            if (this.resumedActivity != null && !getLoggerCatThread().isAlive()) {
                getLoggerCatThread().start();
            }
            else {
                getLoggerCatThread().interrupt();
                this.loggerCatThread = null;
            }
        }

        return this;
    }
    protected Activity getResumedActivity() {
        return resumedActivity;
    }

    private HashMap<Activity, LoggerDisplayView> loggerDisplayViewHashMap;
    protected HashMap<Activity, LoggerDisplayView> getLoggerDisplayViewHashMap() {
        if (this.loggerDisplayViewHashMap == null) {
            this.loggerDisplayViewHashMap = new HashMap<Activity, LoggerDisplayView>();
        }
        return this.loggerDisplayViewHashMap;
    }

    private LoggerCatThread loggerCatThread;
    protected LoggerCatThread getLoggerCatThread() {
        if (this.loggerCatThread == null) {
            this.loggerCatThread = new LoggerCatThread();
            this.loggerCatThread.setTag(getDisplayTag());
        }
        return this.loggerCatThread;
    }

    private String displayTag;
    public LoggerDisplay setDisplayTag(String displayTag) {
        this.displayTag = displayTag;
        getLoggerCatThread().setTag(this.displayTag);
        return this;
    }
    public String getDisplayTag() {
        return this.displayTag;
    }

    /* Overrides */
     
     
    /* Delegates */
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        setResumedActivity(activity);

        if (Logger.isDebugging()) {
            if (!getLoggerDisplayViewHashMap().containsKey(activity)) {
                LoggerDisplayView loggerDisplayView = new LoggerDisplayView(activity);
                getLoggerDisplayViewHashMap().put(activity, loggerDisplayView);
            }

            getLoggerDisplayViewHashMap().get(activity).attachToActivity();
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (activity == getResumedActivity()) {
            setResumedActivity(null);
        }

        if (Logger.isDebugging()) {
            if (getLoggerDisplayViewHashMap().containsKey(activity)) {
                getLoggerDisplayViewHashMap().get(activity).detachFromActivity();
            }
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (activity == getResumedActivity()) {
            setResumedActivity(null);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (activity == getResumedActivity()) {
            setResumedActivity(null);
        }

        getLoggerDisplayViewHashMap().remove(activity);
    }
     
     
    /* Private Methods */
    
}
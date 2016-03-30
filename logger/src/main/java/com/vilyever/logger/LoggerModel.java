package com.vilyever.logger;

import java.util.ArrayList;

/**
 * LoggerModel
 * AndroidLogger <com.vilyever.logger>
 * Created by vilyever on 2016/3/29.
 * Feature:
 */
public class LoggerModel {
    final LoggerModel self = this;

    
    /* Constructors */
    
    
    /* Public Methods */
    public static ArrayList<LoggerModel> loggerModels;
    public static synchronized ArrayList<LoggerModel> getLoggerModels() {
        if (loggerModels == null) {
            loggerModels = new ArrayList<>();
        }

        return loggerModels;
    }
    
    
    /* Properties */
    private String logcatMessage;
    public LoggerModel setLogcatMessage(String logcatMessage) {
        this.logcatMessage = logcatMessage;

        String[] components = logcatMessage.split(" ");
        if (components.length > 5) {
            setDate(components[0]);
            setTime(components[1]);
            setProcessID(components[2]);
            setThreadID(components[3]);
            setLogLevel(LogLevel.fromFlag(components[4]));

            String tagWithContent = logcatMessage.substring(components[0].length() + 1 + components[1].length() + 1 + components[2].length() + 1 + components[3].length() + 1 + components[4].length() + 1);
            setTagWithContent(tagWithContent);
        }

        return this;
    }
    public String getLogcatMessage() {
        return this.logcatMessage;
    }

    private String date;
    protected LoggerModel setDate(String date) {
        this.date = date;
        return this;
    }
    public String getDate() {
        return this.date;
    }

    private String time;
    protected LoggerModel setTime(String time) {
        this.time = time;
        return this;
    }
    public String getTime() {
        return this.time;
    }

    private String processID;
    protected LoggerModel setProcessID(String processID) {
        this.processID = processID;
        return this;
    }
    public String getProcessID() {
        return this.processID;
    }

    private String threadID;
    protected LoggerModel setThreadID(String threadID) {
        this.threadID = threadID;
        return this;
    }
    public String getThreadID() {
        return this.threadID;
    }

    private LogLevel logLevel;
    protected LoggerModel setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
        return this;
    }
    public LogLevel getLogLevel() {
        if (this.logLevel == null) {
            return LogLevel.Debug;
        }
        return this.logLevel;
    }

    private String tagWithContent;
    protected LoggerModel setTagWithContent(String tagWithContent) {
        this.tagWithContent = tagWithContent;
        return this;
    }
    public String getTagWithContent() {
        return this.tagWithContent;
    }


    /* Overrides */
     
     
    /* Delegates */
     
     
    /* Private Methods */

    public enum LogLevel {
        Debug, Info, Warn, Error, Assert;
        public static LogLevel fromFlag(String flag) {
            switch (flag) {
                case "D":
                    return Debug;
                case "I":
                    return Info;
                case "W":
                    return Warn;
                case "E":
                    return Error;
                case "A":
                    return Assert;
            }

            return Debug;
        }
    }
}
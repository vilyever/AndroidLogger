package com.vilyever.logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * LoggerCatThread
 * AndroidLogger <com.vilyever.logger>
 * Created by vilyever on 2016/3/29.
 * Feature:
 */
public class LoggerCatThread extends Thread {
    final LoggerCatThread self = this;

    private static final String ProcessID = Integer.toString(android.os.Process.myPid());
    
    /* Constructors */

    /* Public Methods */
    
    
    /* Properties */
    private String tag;
    public LoggerCatThread setTag(String tag) {
        this.tag = tag;
        return this;
    }
    public String getTag() {
        if (this.tag == null) {
            this.tag = "";
        }
        return this.tag;
    }

    /* Overrides */
    @Override
    public void run() {
        super.run();

        BufferedReader bufferedReader = null;
        try {
            String[] command = new String[]{"logcat", "-v", "threadtime"};

            Process process = Runtime.getRuntime().exec(command);

            bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while (!isInterrupted() && (line = bufferedReader.readLine()) != null) {
                if (line.contains(ProcessID)) {
                    LoggerModel model = new LoggerModel().setLogcatMessage(line);
                    int tagIndex = model.getTagWithContent().indexOf(getTag());
                    if (tagIndex >=0 && tagIndex <= getTag().length()) {
                        LoggerModel.getLoggerModels().add(model);
                        LoggerDisplay.notifyLogChanged();
                    }
                }
            }

        }
        catch (Exception e) {
//            e.printStackTrace();
        }
        finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                }
                catch (IOException e) {
//                    e.printStackTrace();
                }
            }
        }
    }


    /* Delegates */
     
     
    /* Private Methods */
    
}
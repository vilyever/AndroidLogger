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

    private static final String processID = Integer.toString(android.os.Process.myPid());
    
    /* Constructors */

    /* Public Methods */
    
    
    /* Properties */
    
    
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
                if (line.contains(processID)) {
                    LoggerModel.getLoggerModels().add(new LoggerModel().setLogcatMessage(line));
                    LoggerDisplay.notifyLogChanged();
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
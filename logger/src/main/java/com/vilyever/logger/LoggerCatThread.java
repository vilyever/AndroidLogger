package com.vilyever.logger;

import java.io.BufferedReader;
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

        while (!isInterrupted()) {
            try {
                String[] command = new String[]{"logcat", "-v", "threadtime"};

                Process process = Runtime.getRuntime().exec(command);

                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.contains(processID)) {
                        LoggerModel.getLoggerModels().add(new LoggerModel().setLogcatMessage(line));
                        LoggerDisplay.notifyLogChanged();
                    }
                }

                bufferedReader.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            Thread.yield();
        }
    }


    /* Delegates */
     
     
    /* Private Methods */
    
}
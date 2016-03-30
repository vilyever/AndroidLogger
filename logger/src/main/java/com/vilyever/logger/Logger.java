package com.vilyever.logger;

import android.app.Application;
import android.util.Log;

/**
 * Logger
 * AndroidLogger <com.vilyever.logger>
 * Created by vilyever on 2016/3/23.
 * Feature:
 */
public class Logger {
    final Logger self = this;

    private static final String DefaultTag = "Logger";

    private static final int LogMaxLength = 2048;

    private static boolean WhetherCheckDebugging = false;
    private static boolean Debugging = false;

    private static String LogDecoratedBegin = "✎﹋✎﹋✎﹋✎﹋✎﹋✎﹋✎﹋✎﹋✎﹋✎﹋✎﹋✎﹋✎﹋✎﹋✎﹋✎﹋✎﹋✎﹋✎﹋✎﹋✎";
    private static String logDecoratedEnd = "✐﹏✐﹏✐﹏✐﹏✐﹏✐﹏✐﹏✐﹏✐﹏✐﹏✐﹏✐﹏✐﹏✐﹏✐﹏✐﹏✐﹏✐﹏✐﹏✐﹏✐";

    /* Constructors */


    /* Public Methods */
    public static void setDebugging(boolean debugging) {
        Debugging = debugging;
    }

    public static boolean isDebugging() {
        if (!WhetherCheckDebugging) {
            checkDebugging();
            WhetherCheckDebugging = true;
        }

        return Debugging;
    }

    public static void log(String msg) {
        logLongMsg(DefaultTag, attachCaller(msg), true);
    }

    public static void log(String tag, String msg) {
        logLongMsg(tag, attachCaller(msg), true);
    }

    public static void logWithoutCaller(String msg) {
        logLongMsg(DefaultTag, msg, true);
    }

    public static void logWithoutCaller(String tag, String msg) {
        logLongMsg(tag, msg, true);
    }

    public static void logWithAllCaller(String msg) {
        logLongMsg(DefaultTag, makeDecoratedMessageWithAllCaller(msg), true);
    }

    public static void logWithAllCaller(String tag, String msg) {
        logLongMsg(tag, makeDecoratedMessageWithAllCaller(msg), true);
    }

    /* Properties */


    /* Overrides */


    /* Delegates */


    /* Private Methods */
    private static String attachCaller(String msg) {
        msg = msg + "    " + getCaller(false);
        return msg;
    }

    private static String makeDecoratedMessageWithAllCaller(String msg) {
        msg = LogDecoratedBegin + "\n" + getCaller(true) + " \n" + msg + "\n \n" + logDecoratedEnd;
        return msg;
    }

    private static void logLongMsg(String tag, String msg, boolean isFirstParagraph) {
        if (isDebugging()) {

            String subMsg = msg;
            if (msg.length() > LogMaxLength) {
                int cutIndex = LogMaxLength;

                subMsg = msg.substring(0, LogMaxLength);

                int lastLineBreakIndex = subMsg.lastIndexOf("\n");
                if (LogMaxLength - lastLineBreakIndex < 256) {
                    subMsg = msg.substring(0, lastLineBreakIndex);
                    cutIndex = lastLineBreakIndex + 1;
                }

                Log.d(tag, subMsg);
                logLongMsg(tag, msg.substring(cutIndex), false);
            }
            else {
                Log.d(tag, subMsg);
            }
        }
    }

    private static String getCaller(boolean showAllCaller) {
        String result = "";

        StackTraceElement stacks[] = (new Throwable()).getStackTrace();

        if (!showAllCaller) {
            for (int i = 0; i < stacks.length; i++) {
                StackTraceElement ste = stacks[i];
                if (!ste.getClassName().equals(Logger.class.getName())) {
                    result += "✏✏✏✏"
                              + "    ✎ ＆Method: " + ste.getClassName() + "." + ste.getMethodName() + "(...)"
                              + "    #Line: " + ste.getLineNumber() + " ✐";
                    break;
                }
            }
        }
        else {
            String space = "";
            for (int i = stacks.length - 1; i >= 0; i--) {
                StackTraceElement ste = stacks[i];

                if (ste.getClassName().equals(Logger.class.getName())) {
                    break;
                }

                result += space;
                result += "✏✏✏✏"
                          + "    ＆Method: " + ste.getClassName() + "." + ste.getMethodName() + "(...)"
                          + "    #Line: " + ste.getLineNumber();
                result += "\n";
                space += "  ";
            }
        }

        return result;
    }

    private static void checkDebugging() {
//        // 从证书判断，不保证正确
//        try {
//            Signature signatures[] = ContextHolder.getContext().getPackageManager().getPackageInfo(ContextHolder.getContext().getPackageName(), PackageManager.GET_SIGNATURES).signatures;
//
//            CertificateFactory cf = CertificateFactory.getInstance("X.509");
//
//            for (Signature signature : signatures) {
//                ByteArrayInputStream stream = new ByteArrayInputStream(signature.toByteArray());
//                X509Certificate cert = (X509Certificate) cf.generateCertificate(stream);
//                String certInfo = cert.getSubjectX500Principal().toString();
//
//                if (certInfo.toLowerCase().contains("debug")) {
//                    setDebugging(true);
//                }
//            }
//        }
//        catch (PackageManager.NameNotFoundException | CertificateException e) {
//            //debuggable variable will remain pre state
//        }

        /**
         * 通过反射获取应用module下的BuildConfig的DEBUG属性
         */
        try {
            Application application = null;
            try {
                application = (Application) Class.forName("android.app.ActivityThread")
                                                             .getMethod("currentApplication").invoke(null, (Object[]) null);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (application == null) {
                try {
                    application = (Application) Class.forName("android.app.AppGlobals")
                                                                 .getMethod("getInitialApplication").invoke(null, (Object[]) null);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (application != null) {
                String className = application.getPackageName() + ".BuildConfig";
                Class<?> buildConfigClazz = Class.forName(className);
                setDebugging((Boolean) buildConfigClazz.getField("DEBUG").get(null));
            }
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
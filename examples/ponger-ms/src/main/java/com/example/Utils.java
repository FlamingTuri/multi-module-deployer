package com.example;

public class Utils {

    /**
     * Java Thread.sleep wrapper. Shortcut to avoid try - catch
     * 
     * @param millis the milliseconds to sleep
     */
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Logging utility. 
     * Print format [{caller class name}]: message (line {caller line number})
     * 
     * @param message the message to log
     */
    public static void log(String message) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement caller = stackTrace[2];
        String callerClassName = caller.getClassName();
        int callerLineNumber = caller.getLineNumber();
        String toPrint = String.format("[%s]: %s (line %d)", callerClassName, message, callerLineNumber);
        System.out.println(toPrint);
    }
}

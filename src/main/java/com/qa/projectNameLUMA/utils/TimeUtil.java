package com.qa.projectNameLUMA.utils;

public class TimeUtil {

    public static final int DEFAULT_TIME = 5;

    public static final int DEFAULT_MEDIUM_TIME = 10;

    public static final int DEFAULT_LONG_TIME = 15;

    public static final int DEFAULT_SuperLONG_TIME = 20;


    /**
     * Method to pause execution for a given number of milliseconds.
     *
     * @param seconds The duration in milliseconds to sleep.
     */
    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Sleep interrupted: " + e.getMessage());
        }
    }
}

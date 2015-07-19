package com.mikimedia.android.util;

public class Logger {

    public static boolean DEBUG = true;

    private static final int LEVEL = android.util.Log.VERBOSE;

    /*
     * VERBOSE
     */
    public static void v(String tag, String msgFormat) {
        if (DEBUG && LEVEL <= android.util.Log.VERBOSE) {
            android.util.Log.v(tag, msgFormat);
        }
    }

    public static void v(String tag, String msgFormat, Object...args) {
        if (DEBUG && LEVEL <= android.util.Log.VERBOSE) {
            android.util.Log.v(tag, String.format(msgFormat, args));
        }
    }

    public static void v(String tag, Throwable t, String msgFormat, Object...args) {
        if (DEBUG && LEVEL <= android.util.Log.VERBOSE) {
            android.util.Log.v(tag, String.format(msgFormat, args), t);
        }
    }

    /*
     * DEBUG
     */

    public static void d(String tag, String msgFormat) {
        if (DEBUG && LEVEL <= android.util.Log.DEBUG) {
            android.util.Log.d(tag,msgFormat);
        }
    }

    public static void d(String tag, String msgFormat, Object...args) {
        if (DEBUG && LEVEL <= android.util.Log.DEBUG) {
            android.util.Log.d(tag, String.format(msgFormat, args));
        }
    }

    public static void d(String tag, Throwable t, String msgFormat, Object...args) {
        if (DEBUG && LEVEL <= android.util.Log.DEBUG) {
            android.util.Log.d(tag, String.format(msgFormat, args), t);
        }
    }

    /*
     * ERROR
     */

    public static void e(String tag, String msgFormat) {
        if (DEBUG && LEVEL <= android.util.Log.ERROR) {
            android.util.Log.v(tag,msgFormat);
        }
    }

    public static void e(String tag, String msgFormat, Object...args) {
        if (DEBUG && LEVEL <= android.util.Log.ERROR) {
            android.util.Log.v(tag, String.format(msgFormat, args));
        }
    }

    public static void e(String tag, Throwable t, String msgFormat, Object...args) {
        if (DEBUG && LEVEL <= android.util.Log.ERROR) {
            android.util.Log.v(tag, String.format(msgFormat, args), t);
        }
    }

}
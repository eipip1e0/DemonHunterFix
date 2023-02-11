package com.android.killer;

public class Log {
    public static void LogInt(int value) {
        android.util.Log.v("AndroidKiller-int", String.valueOf(value));
    }

    public static void LogStr(String value) {
        android.util.Log.v("AndroidKiller-string", value);
    }

    public static void LogBool(boolean value) {
        android.util.Log.v("AndroidKiller-boolean", String.valueOf(value));
    }

    public static void LogFloat(float value) {
        android.util.Log.v("AndroidKiller-float", String.valueOf(value));
    }

    public static void LogDouble(double value) {
        android.util.Log.v("AndroidKiller-double", String.valueOf(value));
    }

    public static void LogChar(char value) {
        android.util.Log.v("AndroidKiller-char", String.valueOf(value));
    }

    public static void LogLong(long value) {
        android.util.Log.v("AndroidKiller-long", String.valueOf(value));
    }

    public static void LogChars(char[] value) {
        android.util.Log.v("AndroidKiller-char[]", String.valueOf(value));
    }

    public static void LogShort(short value) {
        android.util.Log.v("AndroidKiller-short", String.valueOf((int) value));
    }
}

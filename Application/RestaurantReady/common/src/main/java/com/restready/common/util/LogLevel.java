package com.restready.common.util;

public final class LogLevel {

    public static final int None  = 0x20; // Nothing is logged.
    public static final int Error = 0x10; // Critical errors. The application may no longer work correctly.
    public static final int Warn  = 0x08; // Important warnings. The application should still work correctly.
    public static final int Info  = 0x04; // Informative messages. Useful for production.
    public static final int Debug = 0x02; // Useful during development.
    public static final int Trace = 0x01; // A lot of information is logged.

    public static String toName(int level) {
        return switch (level) {
            case LogLevel.Error -> "ERROR";
            case LogLevel.Warn -> " WARN";
            case LogLevel.Info -> " INFO";
            case LogLevel.Debug -> "DEBUG";
            case LogLevel.Trace -> "TRACE";
            default -> "";
        };
    }
}

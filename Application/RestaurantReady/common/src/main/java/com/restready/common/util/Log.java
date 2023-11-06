package com.restready.common.util;

@SuppressWarnings("ConstantConditions")
public class Log {

    /** Compiling this and the booleans below as "final" will cause the compiler
     * to remove all "if (LogLevel.Info) ..." type statements below the set level. */
    private static int _logLevel = LogLevel.Info;
    private static Logger _logger = new Logger();

    public static boolean ERROR = (_logLevel | LogLevel.Error) > 0;
    public static boolean WARN = (_logLevel | LogLevel.Warn) > 0;
    public static boolean INFO = (_logLevel | LogLevel.Info) > 0;
    public static boolean DEBUG = (_logLevel | LogLevel.Debug) > 0;
    public static boolean TRACE = (_logLevel | LogLevel.Trace) > 0;

    private Log() { }

    /** Comment out this method's body when compiling fixed-level JARs. */
    public static void setLogLevels(int level) {
        _logLevel = level;
        ERROR = (_logLevel | LogLevel.Error) > 0;
        WARN = (_logLevel | LogLevel.Warn) > 0;
        INFO = (_logLevel | LogLevel.Info) > 0;
        DEBUG = (_logLevel | LogLevel.Debug) > 0;
        TRACE = (_logLevel | LogLevel.Trace) > 0;
    }

    public static int getLogLevel() {
        return _logLevel;
    }

    public static void setLogger(Logger logger) {
        Log._logger = logger;
    }

    //region Error
    public static void error(String message, Throwable ex) {
        if (ERROR) _logger.log(LogLevel.Error, null, message, ex);
    }

    public static void error(Object category, String message, Throwable ex) {
        if (ERROR) _logger.log(LogLevel.Error, category.getClass().getSimpleName(), message, ex);
    }

    public static void error(String message) {
        if (ERROR) _logger.log(LogLevel.Error, null, message, null);
    }

    public static void error(Object category, String message) {
        if (ERROR) _logger.log(LogLevel.Error, category.getClass().getSimpleName(), message, null);
    }
    //endregion

    //region Warn
    public static void warn(String message, Throwable ex) {
        if (WARN) _logger.log(LogLevel.Warn, null, message, ex);
    }

    public static void warn(Object category, String message, Throwable ex) {
        if (WARN) _logger.log(LogLevel.Warn, category.getClass().getSimpleName(), message, ex);
    }

    public static void warn(String message) {
        if (WARN) _logger.log(LogLevel.Warn, null, message, null);
    }

    public static void warn(Object category, String message) {
        if (WARN) _logger.log(LogLevel.Warn, category.getClass().getSimpleName(), message, null);
    }
    //endregion

    //region Info
    public static void info(String message, Throwable ex) {
        if (INFO) _logger.log(LogLevel.Info, null, message, ex);
    }

    public static void info(Object category, String message, Throwable ex) {
        if (INFO) _logger.log(LogLevel.Info, category.getClass().getSimpleName(), message, ex);
    }

    public static void info(String message) {
        if (INFO) _logger.log(LogLevel.Info, null, message, null);
    }

    public static void info(Object category, String message) {
        if (INFO) _logger.log(LogLevel.Info, category.getClass().getSimpleName(), message, null);
    }
    //endregion

    //region Debug
    public static void debug(String message, Throwable ex) {
        if (DEBUG) _logger.log(LogLevel.Debug, null, message, ex);
    }

    public static void debug(Object category, String message, Throwable ex) {
        if (DEBUG) _logger.log(LogLevel.Debug, category.getClass().getSimpleName(), message, ex);
    }

    public static void debug(String message) {
        if (DEBUG) _logger.log(LogLevel.Debug, null, message, null);
    }

    public static void debug(Object category, String message) {
        if (DEBUG) _logger.log(LogLevel.Debug, category.getClass().getSimpleName(), message, null);
    }
    //endregion

    //region Trace
    public static void trace(String message, Throwable ex) {
        if (TRACE) _logger.log(LogLevel.Trace, null, message, ex);
    }

    public static void trace(Object category, String message, Throwable ex) {
        if (TRACE) _logger.log(LogLevel.Trace, category.getClass().getSimpleName(), message, ex);
    }

    public static void trace(String message) {
        if (TRACE) _logger.log(LogLevel.Trace, null, message, null);
    }

    public static void trace(Object category, String message) {
        if (TRACE) _logger.log(LogLevel.Trace, category.getClass().getSimpleName(), message, null);
    }
    //endregion
}

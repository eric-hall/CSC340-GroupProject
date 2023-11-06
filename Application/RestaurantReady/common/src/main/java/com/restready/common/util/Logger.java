package com.restready.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

// Default implementation logs to System.out
public class Logger {

    private final long _firstLogTime = System.currentTimeMillis();

    public void log(int level, String category, String message, Throwable ex) {

        StringBuilder builder = new StringBuilder(256);

        long milliseconds = System.currentTimeMillis() - _firstLogTime;
        long minutes = (milliseconds / 1000) / 60;
        long seconds = (milliseconds / 1000) % 60;

        if (minutes <= 9) builder.append('0');
        builder.append(minutes);
        builder.append(':');
        if (seconds <= 9) builder.append('0');
        builder.append(seconds);

        builder.append(" ");
        builder.append(LogLevel.toName(level));
        builder.append(": ");

        if (category != null) {
            builder.append("[");
            builder.append(category);
            builder.append("] ");
        }

        builder.append(message);

        if (ex != null) {
            StringWriter writer = new StringWriter(256);
            ex.printStackTrace(new PrintWriter(writer));
            builder.append('\n');
            builder.append(writer.toString().trim());
        }

        System.out.println(builder);
    }
}

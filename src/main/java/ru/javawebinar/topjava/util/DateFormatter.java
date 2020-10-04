package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateFormatter {
    private DateFormatter() {
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
        if (localDateTime != null)
            return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
        return "";
    }
}

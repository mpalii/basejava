package ru.javaops.webapp.util;

import java.time.LocalDate;
import java.time.Month;

public final class DateUtil {
    private DateUtil() {}

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);
}

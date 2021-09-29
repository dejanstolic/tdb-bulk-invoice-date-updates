package com.brandmaker.cs.skyhigh.tdb.webapi.helpers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MaplUtils {

    public static String formatDate(final Date date) {
        return date
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static String formatDateToUTC(final Date date) {
        return date
                .toInstant()
                .atZone(ZoneId.of("UTC"))
                .toLocalDate()
                .format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static Date getMaxMaplDate() {
        return parseDate("9999-12-31");
    }

    public static Date parseDate(final String dateText) {
        final LocalDate maxDate = LocalDate.parse(dateText, DateTimeFormatter.ISO_LOCAL_DATE);
        return Date.from(maxDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date localDateToDate(final LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.of("UTC")).toInstant());
    }

    public static LocalDate parseToLocalDate(final String dateText) {
        return LocalDate.parse(dateText, DateTimeFormatter.ISO_LOCAL_DATE);
    }
}

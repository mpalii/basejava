package ru.javaops.webapp;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainDate {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Date date = new Date();
        System.out.println(date);
        System.out.println(System.currentTimeMillis() - start);

        Calendar calendar = Calendar.getInstance();

        for (String zone : TimeZone.getAvailableIDs()) {
            System.out.println(zone);
        }

        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Kiev"));
        System.out.println(calendar.getTime());

        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        System.out.println(localDateTime);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YY/MM/dd");
        System.out.println(simpleDateFormat.format(date));

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YY/MMMM/dd");
        System.out.println(dateTimeFormatter.format(localDateTime));
    }
}

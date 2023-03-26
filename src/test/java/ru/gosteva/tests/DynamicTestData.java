package ru.gosteva.tests;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class DynamicTestData {

    static LocalDate tomorrowDate = LocalDate.now().plusDays(1);
    static Date date = java.sql.Date.valueOf(tomorrowDate);
    static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    public static String formattedTomorrowDate = formatter.format(date);
    public static String tomorrow = formattedTomorrowDate.replaceAll("[^\\d]", "");
}

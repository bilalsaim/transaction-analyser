package com.bilisel.bianalyser.util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

public class DateHelper {

    private static final String[] dateFormats = new String[] { "dd/MM/yyyy HH:mm:ss" };

    private DateHelper() {

    }

    public static Date parseDate(String date) throws ParseException {
        return DateUtils.parseDate(date, dateFormats);
    }
}

package com.epam.esm.utils.date;

import com.epam.esm.exception.types.InputException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * IsoDateFormatter is the utility class, used for date operations
 */
public final class IsoDateFormatter {

    /**
     * Constant field that hold format of ISO8601
     */
    private static final String ISO8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    /**
     * Default constructor
     */
    private IsoDateFormatter() {}

    /**
     * Converts requested date to string
     *
     * @param date requested date
     * @return {@code String} converted date
     */
    public static String dateToString(Date date) {
        return new SimpleDateFormat(ISO8601_FORMAT).format(date);
    }

    /**
     * Converts requested string to date
     * Note: if string doesn't match ISO8601 format InputException thrown
     *
     * @param d requested string
     * @return {@code Date} converted string
     */
    public static Date stringToDate(String d) {
        try {
            return new SimpleDateFormat(ISO8601_FORMAT).parse(d);
        } catch (ParseException e) {
            throw new InputException("Date is corrupted or wrong format used. Use 'yyyy-MM-ddThh:mm:ss.SSSS' format");
        }
    }

    /**
     * Checks if requested date is in ISO8601 format
     *
     * @param d requested string
     * @return {@code true} if string matches ISO8601 date format
     */
    public static boolean checkIfIsoDate(String d) {
        try {
            new SimpleDateFormat(ISO8601_FORMAT).parse(d);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
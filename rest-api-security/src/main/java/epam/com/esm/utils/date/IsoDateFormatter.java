package epam.com.esm.utils.date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * IsoDateFormatter is the utility class, converts date between string and local date time
 */
public final class IsoDateFormatter {

    /**
     * ISO8601 pattern
     */
    private static final String ISO8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    /**
     * Default constructor
     */
    private IsoDateFormatter() {}

    /**
     * Converts local date time value to string
     *
     * @param localDateTime date time to be converted
     * @return {@code String} converted date time
     */
    public static String dateToString(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(ISO8601_FORMAT));
    }

    /**
     * Converts string value to local date time
     *
     * @param d string to be converted
     * @return {@code LocalDateTime} converted string
     */
    public static LocalDateTime stringToDate(String d) {
        return LocalDateTime.from(DateTimeFormatter.ofPattern(ISO8601_FORMAT).parse(d));
    }

    /**
     * Checks if string is ISO8601 format
     *
     * @param d requested string
     * @return {@code true} if ISO8601 format
     */
    public static boolean checkIfIsoDate(String d) {
        try {
            DateTimeFormatter.ofPattern(ISO8601_FORMAT).parse(d);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
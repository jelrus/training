import java.util.Arrays;

/**
 * <p>Utils immutable utility class that provides operations on String varargs</p>
 *
 * <p>Operations presented in the class:</p>
 *
 * <ul>
 *      <li><b>isAllPositiveNumbers</b> - checks if String vararg contains only positive numbers</li>
 * </ul>
 *
 * @since 1.0
 */
public final class Utils {


    /**
     * <p>Default constructor</p>
     */
    private Utils() {}

    /**
     * <p>Checks if all presented numbers in String vararg are positive</p>
     *
     * @param str the String array, may be null
     * @return {@code true} if all strings presented in vararg are positive numbers
     * @since 1.0
     */
    public static boolean isAllPositiveNumbers(String... str) {
        return str != null && str.length != 0 && Arrays.stream(str).allMatch(StringUtils::isPositiveNumber);
    }
}
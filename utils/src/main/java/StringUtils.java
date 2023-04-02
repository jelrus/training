/**
 * <p>StringUtils immutable utility class that provides operations on {@link java.lang.String}</p>
 *
 * <p>Operations presented in the class:</p>
 *
 * <ul>
 *     <li><b>isPositiveNumber</b> - checks if String is positive number</li>
 * </ul>
 *
 * @since 1.3.5
 */
public final class StringUtils {

    /**
     * <p>Default constructor</p>
     */
    private StringUtils() {}

    /**
     * <p>Checks if String is positive number</p>
     *
     * <p>Note that the method allows positive leading sign ('+').
     * May generate a NumberFormatException error if the value is outside of the long range.</p>
     *
     * <pre>
     *     <u><b>False cases if value contains:</b></u>
     *     StringUtils.isPositiveNumber(null) = false     - null value
     *     StringUtils.isPositiveNumber("") = false       - empty value
     *     StringUtils.isPositiveNumber(" ") = false      - blank value
     *     StringUtils.isPositiveNumber("\t") = false     - tabulation character
     *     StringUtils.isPositiveNumber("\n") = false     - new line character
     *     StringUtils.isPositiveNumber("59 3") = false   - space or whitespace inside value
     *     StringUtils.isPositiveNumber("cdf1a") = false  - alphanumeric value
     *     StringUtils.isPositiveNumber("35-8") = false   - symbol-numeric values (except leading positive sign or zero(s))
     *     StringUtils.isPositiveNumber("40.6") = false   - decimal number with fractions
     *
     *     <u><b>Exception case if value consists of:</b></u>
     *     StringUtils.isPositiveNumber("9223372036854775808") - generates NumberFormatException error
     *     Note! Long.MaxValue = 9223372036854775807
     *
     *     <u><b>True cases if value consists of:</b></u>
     *     StringUtils.isPositiveNumber("123") = true                 - positive unsigned number
     *     StringUtils.isPositiveNumber("+123") = true                - positive signed number
     *     StringUtils.isPositiveNumber("\u0967\u0968\u0969") = true  - unicode digits
     *     StringUtils.isPositiveNumber("01235") = true               - number value with leading zeros
     * </pre>
     *
     * @param str the String to check, may be null
     * @return {@code true} if only positive number according to the mentioned true cases
     * @since 1.3.5
     */
    public static boolean isPositiveNumber(String str) {
        return str != null && str.length() > 0 && str.toCharArray()[0] == '+' ?
                org.apache.commons.lang3.StringUtils.isNumeric(str.substring(1)) && Long.parseLong(str) > 0 :
                org.apache.commons.lang3.StringUtils.isNumeric(str) && Long.parseLong(str) > 0;
    }
}
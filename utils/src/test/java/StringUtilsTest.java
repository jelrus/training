import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class StringUtilsTest {

    @ParameterizedTest()
    @ValueSource(strings = {"23", "+38", "000272", "\u0967\u0968\u0969"})
    public void isPositiveNumber(String str){
        assertTrue(StringUtils.isPositiveNumber(str));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"-20", "59 3", "cdf1a", "35-8", "40.6"})
    public void isNotNegativeNumberOrAlphaNumeric(String str) {
        assertFalse(StringUtils.isPositiveNumber(str));
    }

    @ParameterizedTest()
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    public void isNotNullOrEmpty(String str) {
        assertFalse(StringUtils.isPositiveNumber(str));
    }

    @ParameterizedTest
    @ValueSource(strings = {"9223372036854775808", "9223372036854775850", "9223372036854776500"})
    public void isThrowingErrorIfOutOfLongValueRange(String str) {
        assertThrows(NumberFormatException.class, () -> StringUtils.isPositiveNumber(str));
    }
}
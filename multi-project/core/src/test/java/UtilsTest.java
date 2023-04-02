import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UtilsTest {

    @ParameterizedTest
    @MethodSource("provideStringArrayWithTaskValues")
    public void isTaskValuesTrue(String... str) {
        assertTrue(Utils.isAllPositiveNumbers(str));
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void isNotNullAndEmptySource(String... str) {
        assertFalse(Utils.isAllPositiveNumbers(str));
    }

    @ParameterizedTest
    @MethodSource("provideStringArraysWithPositiveNumbers")
    public void isStringsContainsPositiveNumbersOnly(String... str) {
        assertTrue(Utils.isAllPositiveNumbers(str));
    }

    @ParameterizedTest
    @MethodSource("provideStringArraysWithNegativeNumbersOrAlphaNumericValues")
    public void isStringsContainsNegativeNumbersOrAlphaNumericValues(String... str) {
        assertFalse(Utils.isAllPositiveNumbers(str));
    }

    @ParameterizedTest
    @MethodSource("provideStringArraysWithEmptyOrNullValues")
    public void isStringsContainsEmptyOrNullValues(String... str) {
        assertFalse(Utils.isAllPositiveNumbers(str));
    }

    static Stream<Arguments> provideStringArrayWithTaskValues() {
        return Stream.of(Arguments.of((Object) new String[]{"12", "79"}));
    }

    static Stream<Arguments> provideStringArraysWithPositiveNumbers() {
        return Stream.of(
                Arguments.of((Object) new String[]{"1", "+2", "3"}),
                Arguments.of((Object) new String[]{"+4", "+5", "+6"}),
                Arguments.of((Object) new String[]{"7", "+8", "9"})
        );
    }

    static Stream<Arguments> provideStringArraysWithNegativeNumbersOrAlphaNumericValues() {
        return Stream.of(
                Arguments.of((Object) new String[]{"-15", "72 0", "qxp29r", "52-6", "12.7", "41-78", "1.9"}),
                Arguments.of((Object) new String[]{"91", "-20", "33"}),
                Arguments.of((Object) new String[]{"24", "+95", "59 3"}),
                Arguments.of((Object) new String[]{"cdf1a", "+79", "9"}),
                Arguments.of((Object) new String[]{"63", "+18", "35-8"}),
                Arguments.of((Object) new String[]{"7", "35", "35-8"}),
                Arguments.of((Object) new String[]{"6", "40.6", "38"})
        );
    }

    static Stream<Arguments> provideStringArraysWithEmptyOrNullValues() {
        return Stream.of(
                Arguments.of((Object) new String[]{null, "+10", "5", "273", "+38"}),
                Arguments.of((Object) new String[]{"150", "", "1359", "+89", "+12"}),
                Arguments.of((Object) new String[]{"179", " ", "542", "+610", "694"}),
                Arguments.of((Object) new String[]{"238", "+112", "\t", "+23", "75"}),
                Arguments.of((Object) new String[]{"856", "473", "+76", "\n", "10"})
        );
    }
}
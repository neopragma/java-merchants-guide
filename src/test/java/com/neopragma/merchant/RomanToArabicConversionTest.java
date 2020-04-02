package com.neopragma.merchant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RomanToArabicConversionTest {

    private Converter converter;

    @BeforeEach
    void setup() {
        converter = new Converter();
    }

    @ParameterizedTest(name = "roman {0} to arabic {1}")
    @MethodSource("romanToArabicProvider")
    void it_converts_roman_numerals_to_arabic_numerals(String roman, int arabic) {
        assertEquals(arabic, converter.toArabic(roman));
    }

    private static Stream<Arguments> romanToArabicProvider() {
        return Stream.of(
                Arguments.of("I", 1),
                Arguments.of("II", 2),
                Arguments.of("III", 3),
                Arguments.of("IV", 4),
                Arguments.of("V", 5),
                Arguments.of("VI", 6),
                Arguments.of("VII", 7),
                Arguments.of("VIII", 8),
                Arguments.of("IX", 9),
                Arguments.of("XXVII", 27),
                Arguments.of("XXXIII", 33),
                Arguments.of("MMCDXCIX", 2499),
                Arguments.of("MMMCMXLIX", 3949)
        );
    }

    @ParameterizedTest(name = "null or empty roman numeral value passed to converter")
    @NullAndEmptySource
    void it_handles_null_and_empty_input_values(String romanNumerals) {
        assertThrows(IllegalArgumentException.class, () -> {
            converter.toArabic(romanNumerals);
        });
    }

    @ParameterizedTest(name = "attempt to convert malformed roman numeral string")
    @ValueSource(strings = { "IVZLII", "ZIX", "LCLZ" })
    void it_handles_malformed_roman_numeral_strings(String invalidNumerals) {
        assertThrows(IllegalArgumentException.class, () -> {
            converter.toArabic(invalidNumerals);
        });
    }


}

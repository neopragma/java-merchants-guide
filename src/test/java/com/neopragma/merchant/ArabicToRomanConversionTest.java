package com.neopragma.merchant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArabicToRomanConversionTest {

    private Converter converter;

    @BeforeEach
    void setup() {
        converter = new Converter();
    }

    @ParameterizedTest(name = "arabic {0} to roman {1}")
    @MethodSource("arabicToRomanProvider")
    void it_converts_arabic_numerals_to_roman_numerals(int arabic, String roman) {
        assertEquals(roman, converter.toRoman(arabic));
    }

    private static Stream<Arguments> arabicToRomanProvider() {
        return Stream.of(
                Arguments.of(1, "I"),
                Arguments.of(2, "II"),
                Arguments.of(3, "III"),
                Arguments.of(4, "IV"),
                Arguments.of(5, "V"),
                Arguments.of(6, "VI"),
                Arguments.of(7, "VII"),
                Arguments.of(8, "VIII"),
                Arguments.of(9, "IX"),
                Arguments.of(27, "XXVII"),
                Arguments.of(33, "XXXIII"),
                Arguments.of(2499, "MMCDXCIX"),
                Arguments.of(3949, "MMMCMXLIX")
        );
    }

}
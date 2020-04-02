package com.neopragma.merchant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Converter implements MerchantConstants {
    private static final int[] ARABIC_DIGITS =
            { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
    private static final String[] ROMAN_DIGITS =
            { "M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I" };

    public String toRoman(int arabic) {
        String roman = EMPTY_STRING;
        for (int i = 0; i < ARABIC_DIGITS.length; i++) {
            while (arabic >= ARABIC_DIGITS[i]) {
                roman += ROMAN_DIGITS[i];
                arabic -= ARABIC_DIGITS[i];
            }
        }
        return roman;
    }

    public int toArabic(String roman) {
        assertInputValueIsNotNullOrEmpty(roman);
        int arabic = 0;
        while (roman.length() > 0) {
            for (int i = 0; i < ROMAN_DIGITS.length; i++) {
                if (VALID_ROMAN_DIGITS.contains(roman.substring(0,1))) {
                    if (roman.startsWith(ROMAN_DIGITS[i])) {
                        arabic += ARABIC_DIGITS[i];
                        roman = roman.substring(ROMAN_DIGITS[i].length());
                        break;
                    }
                } else {
                    throw new IllegalArgumentException();
                }
            }

        }
        return arabic;
    }

    private void assertInputValueIsNotNullOrEmpty(String roman) {
        if (null == roman || roman.equals(EMPTY_STRING)) {
            throw new IllegalArgumentException();
        }
    }

}

package com.neopragma.merchant;

public interface MerchantConstants {
    static final String VALID_ROMAN_DIGITS = "IVXLCDM";
    static final String ALIEN_NUMBER_DECLARATION_PATTERN = "^(.*) is [IVXLCDM]$";
    static final String HOW_MUCH_IS_PATTERN = "^How much is (.*) \\?$";
    static final String REPORT_OF_SALE_PATTERN = "^(.*) is \\d+ Credits";
    static final String PRICE_REQUEST_PATTERN = "^how many Credits is (.*) \\?$";
    static final int MAX_INPUT_LINE_LENGTH = 100;
    static final String EMPTY_STRING = "";
}

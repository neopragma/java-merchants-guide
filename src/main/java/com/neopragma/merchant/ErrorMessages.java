package com.neopragma.merchant;

public interface ErrorMessages {

    final static String ERR_COMMODITY_NAME_MISSING = "The commodity name must be provided";
    final static String ERR_INPUT_LENGTH_EXCEEDS_MAXIMUM_ALLOWED = "The length of the input value exceeds the allowed maximum of %d";
    final static String ERR_SALE_REPORT_FORMAT = "The phrase must follow the pattern: [quantity] [commodity] is [total price] Credits";
    final static String ERR_UNIT_PRICE_OUT_OF_RANGE = "The unit price must be greater than zero; value found was %d";
}

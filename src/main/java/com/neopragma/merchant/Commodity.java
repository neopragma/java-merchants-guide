package com.neopragma.merchant;

public class Commodity implements MerchantConstants, ErrorMessages {

    String name;
    int unitPrice;

    public Commodity(String name, int unitPrice) {
        if (null == name || EMPTY_STRING.equals(name)) {
            throw new IllegalArgumentException(ERR_COMMODITY_NAME_MISSING);
        }
        if (name.length() > MAX_INPUT_LINE_LENGTH){
            throw new IllegalArgumentException(
                    String.format(ERR_INPUT_LENGTH_EXCEEDS_MAXIMUM_ALLOWED, MAX_INPUT_LINE_LENGTH));
        }
        if (unitPrice < 1) {
            throw new IllegalArgumentException(
                    String.format(ERR_UNIT_PRICE_OUT_OF_RANGE, unitPrice));
        }
        this.name = name;
        this.unitPrice = unitPrice;
    }
}

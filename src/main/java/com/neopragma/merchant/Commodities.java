package com.neopragma.merchant;

import java.util.HashMap;
import java.util.Map;

public class Commodities implements ErrorMessages {

    AlienNumberDictionary dictionary;
    Converter converter;
    private Map<String, Commodity> commodities;

    public Commodities(AlienNumberDictionary dictionary, Converter converter) {
        this.dictionary = dictionary;
        this.converter = converter;
        commodities = new HashMap();
    }

    /**
     * @param reportOfSale - String of the form "glob prok Silver is 50 Credits".
     * Store a Commodity object with name "Silver" and unit price derived from
     *    total price and quantity sold, where quantity is "glob prok" in this example.
     */
    public void storeCommodity(String reportOfSale) {
        String[] tokens = reportOfSale.split(" ");
        int nextIndex = 0;
        int quantity = 0;
        for (int i = 0; i < reportOfSale.length(); i++) {
            if (dictionary.contains(tokens[i])) {
                quantity += converter.toArabic(dictionary.retrieveRomanNumeralForAlien(tokens[i]));
            } else {
                if (i < 1) {
                    throw new IllegalArgumentException(ERR_SALE_REPORT_FORMAT);
                }
                nextIndex = i;
                break;
            }
        }
        String name = tokens[nextIndex];
        nextIndex += 1;
        if (!tokens[nextIndex].equals("is")) {
            throw new IllegalArgumentException(ERR_SALE_REPORT_FORMAT);
        }
        nextIndex += 1;
        int totalPrice = 0;
        try {
            totalPrice = Integer.parseInt(tokens[nextIndex]);
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
        nextIndex += 1;
        if (!tokens[nextIndex].equals("Credits")) {
            throw new IllegalArgumentException(ERR_SALE_REPORT_FORMAT);
        }
        commodities.put(name, new Commodity(name, totalPrice / quantity));
    }

    public Commodity getCommodity(String name) {
        return commodities.getOrDefault(name, new Commodity("Noname", 1));
    }
}

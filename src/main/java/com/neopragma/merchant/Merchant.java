package com.neopragma.merchant;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Merchant implements MerchantConstants {

    Commodities commodities;
    Pattern alienNumberDeclarationRegex;
    Pattern howMuchIsRegex;
    Pattern reportOfSaleRegex;
    Pattern priceRequestRegex;

    public Merchant(Commodities commodities) {
        this.commodities = commodities;
        alienNumberDeclarationRegex = Pattern.compile(ALIEN_NUMBER_DECLARATION_PATTERN);
        howMuchIsRegex = Pattern.compile(HOW_MUCH_IS_PATTERN);
        reportOfSaleRegex = Pattern.compile(REPORT_OF_SALE_PATTERN);
        priceRequestRegex = Pattern.compile(PRICE_REQUEST_PATTERN);
    }

    public String process(String inputLine) {
       String result = EMPTY_STRING;
       Matcher matcher = alienNumberDeclarationRegex.matcher(inputLine);
       if (matcher.find()) {
           assign(inputLine);
       }
       matcher = howMuchIsRegex.matcher(inputLine);
       if (matcher.find()) {
           result = howMuchIs(inputLine);
       }
       matcher = reportOfSaleRegex.matcher(inputLine);
       if (matcher.find()) {
           commodities.storeCommodity(inputLine);
       }
       matcher = priceRequestRegex.matcher(inputLine);
       if (matcher.find()) {
           result = priceRequest(inputLine);
       }
       return result;
    }

    AlienNumberDictionary dictionary() {
        return this.commodities.dictionary;
    }

    Converter converter() {
        return this.commodities.converter;
    }

    private void assign(String inputLine) {
        commodities.dictionary.assignFrom(inputLine);
    }

    private String howMuchIs(String inputLine) {
        String alienNumberCodes = inputLine.substring("How much is ".length(), inputLine.length()-2);
        StringTokenizer tokens = new StringTokenizer(alienNumberCodes);
        int arabic = 0;
        String roman = EMPTY_STRING;
        while (tokens.hasMoreTokens()) {
            roman += commodities.dictionary.retrieveRomanNumeralForAlien(tokens.nextToken());
        }
        return alienNumberCodes + " is " + commodities.converter.toArabic(roman);
     }

     private String priceRequest(String inputLine) {
         int indexOfFirstAlienNumberCode = inputLine.indexOf(" is ") + 4;
         String alienNumberCodesPlusCommodityName = inputLine
                .substring(indexOfFirstAlienNumberCode, inputLine.length()-2);
         int indexOfCommodityName = alienNumberCodesPlusCommodityName.lastIndexOf(" ");
         String commodityName = alienNumberCodesPlusCommodityName.substring(indexOfCommodityName + 1);
         String alienNumberCodes = alienNumberCodesPlusCommodityName.substring(0, indexOfCommodityName);

         StringTokenizer tokens = new StringTokenizer(alienNumberCodes);
         int arabic = 0;
         String roman = EMPTY_STRING;
         while (tokens.hasMoreTokens()) {
             roman += commodities.dictionary.retrieveRomanNumeralForAlien(tokens.nextToken());
         }
         int quantity = commodities.converter.toArabic(roman);
         int unitPrice = commodities.getCommodity(commodityName).unitPrice;
         return alienNumberCodesPlusCommodityName + " is " + (quantity * unitPrice) + " Credits";
     }
}

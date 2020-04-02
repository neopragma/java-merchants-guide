package com.neopragma.merchant;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MerchantTest {

    static private Merchant merchant;
    static private Converter converter;
    static private AlienNumberDictionary dictionary;
    static private Commodities commodities;

    @BeforeAll
    static void globalSetup() {
        converter = new Converter();
        dictionary = new AlienNumberDictionary();
        commodities = new Commodities(dictionary, converter);
        merchant = new Merchant(commodities);
    }

    @Tag("integration")
    @Order(1)
    @ParameterizedTest(name = "assign alien number tokens to roman numerals")
    @ValueSource(strings = {
            "unum is I",
            "quinque is V",
            "decem is X",
            "quinquaginta is L",
            "centum is C",
            "quingenti is D",
            "milia is M"
    })
    void it_assigns_alien_number_values_to_roman_numerals(String inputLine) {
        merchant.process(inputLine);
        assertEquals(inputLine.substring(inputLine.length()-1),
                merchant.dictionary().retrieveRomanNumeralForAlien(firstTokenFrom(inputLine)));
    }

    @Tag("integration")
    @Order(2)
    @ParameterizedTest(name = "it returns the value of a given series of alien number tokens")
    @MethodSource("alienNumberLookupValuesProvider")
    void it_returns_the_value_of_an_alien_number(
            String inputLine, String alienNumberCodes, int arabic) {
        assertEquals(alienNumberCodes + " is " + arabic, merchant.process(inputLine));
    }

    private static Stream<Arguments> alienNumberLookupValuesProvider() {
        return Stream.of(
                Arguments.of("How much is unum ?", "unum", 1),
                Arguments.of("How much is milia quingenti centum centum unum quinque ?", "milia quingenti centum centum unum quinque", 1704),
                Arguments.of("How much is quinquaginta unum decem ?", "quinquaginta unum decem", 59)
        );
    }

    @Tag("integration")
    @Order(3)
    @ParameterizedTest(name = "it stores the commodity name and derives the unit price based on a sale report")
    @MethodSource("reportOfSaleDataProvider")
    void it_stores_the_commodity_name_and_derives_the_unit_price_based_on_a_sale_report(
            String inputLine, String expectedCommodityName, int expectedUnitPrice) {
        merchant.process(inputLine);
        Commodity commodity = merchant.commodities.getCommodity(expectedCommodityName);
        assertEquals(expectedCommodityName, commodity.name);
        assertEquals(expectedUnitPrice, commodity.unitPrice);
    }

    private static Stream<Arguments> reportOfSaleDataProvider() {
        return Stream.of(
                Arguments.of("unum unum unum Iron is 18 Credits", "Iron", 6),
                Arguments.of("unum unum unum Silver is 180 Credits", "Silver", 60),
                Arguments.of("unum unum unum Gold is 1800 Credits", "Gold", 600)
        );
    }

    @Tag("integration")
    @Order(4)
    @ParameterizedTest(name = "it returns the extended price for a given quantity of a commodity")
    @MethodSource("extendedPriceRequestDataProvider")
    void it_returns_the_extended_price_for_a_given_quantity_of_a_commodity(
            String inputLine, String expectedResult) {
        assertEquals(expectedResult, merchant.process(inputLine));
    }

    private static Stream<Arguments> extendedPriceRequestDataProvider() {
        return Stream.of(
                Arguments.of("how many Credits is decem centum unum decem Iron ?",
                        "decem centum unum decem Iron is 594 Credits"),
                Arguments.of("how many Credits is centum quinquaginta Silver ?",
                        "centum quinquaginta Silver is 9000 Credits"),
                Arguments.of("how many Credits is unum quinque Gold ?",
                        "unum quinque Gold is 2400 Credits")
        );
    }

    private String firstTokenFrom(String value) {
        return value.substring(0, value.indexOf(" "));
    }
}

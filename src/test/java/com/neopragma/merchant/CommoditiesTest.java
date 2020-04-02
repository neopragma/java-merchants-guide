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

public class CommoditiesTest {

    private Commodities commodities;

    @BeforeEach
    void setup() {
        AlienNumberDictionary dictionary = new AlienNumberDictionary();
        dictionary.assignFrom("glob is I");
        dictionary.assignFrom("prok is V");
        dictionary.assignFrom("pish is X");
        dictionary.assignFrom("tagj is L");
        commodities = new Commodities(dictionary, new Converter());

    }

    @ParameterizedTest(name = "store a new commodity")
    @MethodSource("newCommodityProvider")
    void it_stores_commodity_information_based_on_a_sale(
            String reportOfSale, String commodityName, int expectedUnitPrice) {
        commodities.storeCommodity(reportOfSale);
        assertEquals(expectedUnitPrice, commodities.getCommodity(commodityName).unitPrice);
    }

    private static Stream<Arguments> newCommodityProvider() {
        return Stream.of(
                Arguments.of("glob glob Silver is 50 Credits", "Silver", 25),
                Arguments.of("tagj glob pish Gold is 236 Credits", "Gold", 3)
        );
    }

    @ParameterizedTest(name = "reject malformed sale reports")
    @ValueSource(strings = {
            "flork glob Silver is 50 Credits",
            "glob glob Gold Platinum is 50 Credits",
            "glob glob Gold are 50 Credits",
            "glob glob Gold is fifty Credits",
            "glob glob Gold is 50 Simoleons",
            "not enough tokens"
    })
    void it_handles_malformed_input(String reportOfSale) {
        assertThrows(IllegalArgumentException.class, () -> {
            commodities.storeCommodity(reportOfSale);
        });
    }

    @ParameterizedTest(name = "it returns a default Commodity when the requested one is not found")
    @NullAndEmptySource
    void it_returns_default_commodity(String name) {
        assertEquals("Noname", commodities.getCommodity(name).name);
    }


}

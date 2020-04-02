package com.neopragma.merchant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommodityTest implements MerchantConstants {

    @ParameterizedTest(name = "commodity is properly initialized")
    @MethodSource("validCommodityValueProvider")
    void it_initializes_a_commodity_object(String name, int unitPrice) {
        Commodity commodity = new Commodity(name, unitPrice);
        assertEquals(name, commodity.name);
    }

    private static Stream<Arguments> validCommodityValueProvider() {
        return Stream.of(
                Arguments.of("Dirt", 2),
                Arguments.of("Iron", 15),
                Arguments.of("Silver", 50),
                Arguments.of("Gold", 200),
                Arguments.of("Platinum", 300)
        );
    }

    @ParameterizedTest(name = "it rejects null and empty values for name")
    @MethodSource("nullAndEmptyNameProvider")
    void it_rejects_null_and_empty_commodity_names(String name, int unitPrice) {
        assertThrows(IllegalArgumentException.class, () -> {
            new Commodity(name, unitPrice);
        });
    }

    private static Stream<Arguments> nullAndEmptyNameProvider() {
        return Stream.of(
                Arguments.of(null, 1),
                Arguments.of("", 1)
        );
    }

    @Test
    @DisplayName("it rejects names that are too long")
    void it_rejects_names_that_are_too_long() {
        assertThrows(IllegalArgumentException.class, () -> {
            String longName = "X" + String.join("", Collections.nCopies(MAX_INPUT_LINE_LENGTH, "X"));            System.out.println("====> longName length is " + longName.length() + " <====");
            new Commodity(longName, 1);

        });
    }

    @ParameterizedTest(name = "it rejects unit prices less than 1")
    @MethodSource("invalidUnitPriceProvider")
    void it_rejects_unit_prices_less_than_1(String name, int unitPrice) {
        assertThrows(IllegalArgumentException.class, () -> {
           new Commodity(name, unitPrice);
        });
    }

    private static Stream<Arguments> invalidUnitPriceProvider() {
        return Stream.of(
                Arguments.of("Name", 0),
                Arguments.of("Name", -1)
        );
    }
}

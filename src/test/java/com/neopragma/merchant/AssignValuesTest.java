package com.neopragma.merchant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Assigning Roman Numeral values to alien number codes")
public class AssignValuesTest {

    private AlienNumberDictionary alienNumberDictionary;

    @BeforeEach
    void setup() {
        alienNumberDictionary = new AlienNumberDictionary();
    }

    @Nested
    @DisplayName("Valid declarations of alien number codes")
    class ValidDeclarations {

        @ParameterizedTest(name = "valid input, value=\"{0}\"")
        @ValueSource(strings = {
                "glob is I",
                "proc is V",
                "pish is X",
                "tegj is L",
                "yorgl is C",
                "fakh is M"})
        void it_stores_glob_proc_pish_tegj(String alienNumberDeclaration) {
            String expectedValue = alienNumberDeclaration.substring(alienNumberDeclaration.length() - 1);
            String numberCode = alienNumberDeclaration.substring(0, alienNumberDeclaration.indexOf(" "));
            alienNumberDictionary.assignFrom(alienNumberDeclaration);
            assertEquals(expectedValue, alienNumberDictionary.retrieveRomanNumeralForAlien(numberCode));
        }

        @ParameterizedTest(name = "valid input, value=\"{0}\"")
        @ValueSource(strings = {
                "proc is I",
                "pish is V",
                "tegj is X",
                "glob is L",
                "melvin is M",
                "charlie is C"})
        void it_stores_proc_pish_tegj_glob(String alienNumberDeclaration) {
            String expectedValue = alienNumberDeclaration.substring(alienNumberDeclaration.length() - 1);
            String numberCode = alienNumberDeclaration.substring(0, alienNumberDeclaration.indexOf(" "));
            alienNumberDictionary.assignFrom(alienNumberDeclaration);
            assertEquals(expectedValue, alienNumberDictionary.retrieveRomanNumeralForAlien(numberCode));
        }
    }

    @Nested
    @DisplayName("Malformed declarations of alien number codes")
    class InvalidDeclarations {

        @ParameterizedTest(name = "invalid input, value=\"{0}\"")
        @ValueSource(strings = {
                "tegj is Z",
                "proc is IV",
                "NoSpaceAfterFirstToken",
                " LeadingSpace is wrong"})
        void it_handles_invalid_alien_number_declarations(String alienNumberDeclaration) {
            assertThrows(MalformedAlienNumberDeclarationException.class, () -> {
                alienNumberDictionary.assignFrom(alienNumberDeclaration);
            });
        }

        @ParameterizedTest(name = "null and empty input, value=\"{0}\"")
        @NullAndEmptySource
        void it_handles_null_and_empty_input(String alienNumberDeclaration) {
            assertThrows(MalformedAlienNumberDeclarationException.class, () -> {
                alienNumberDictionary.assignFrom(alienNumberDeclaration);
            });
        }
    }

    @Nested
    @DisplayName("Attempts to assign duplicate entries")
    class DuplicateEntries {

        @Test
        void it_handles_attempt_to_assign_roman_numeral_twice() {
            alienNumberDictionary.assignFrom("glob is I");
            alienNumberDictionary.assignFrom("tegj is V");
            assertThrows(DuplicateRomanNumeralEntryException.class, () -> {
                alienNumberDictionary.assignFrom("proc is I");
            });
        }

        @Test
        void it_handles_attempt_to_assign_alien_number_code_twice() {
            alienNumberDictionary.assignFrom("glob is I");
            alienNumberDictionary.assignFrom("tegj is V");
            assertThrows(DuplicateAlienNumberCodeEntryException.class, () -> {
                alienNumberDictionary.assignFrom("glob is X");
            });
        }
    }

    @Nested
    @DisplayName("Security-related checks")
    class SecurityRelatedChecks {

        @Test
        void it_prevents_input_longer_than_the_defined_maximum() {
            assertThrows(InputValueExceedsMaximumAllowableLengthException.class, () -> {
                String longString = "XXXX X";
                String testValue = longString + String.join("", Collections.nCopies(96, "X"));
                alienNumberDictionary.assignFrom(testValue);
            });
        }
    }
}
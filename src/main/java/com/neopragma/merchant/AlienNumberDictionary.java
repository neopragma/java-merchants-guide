package com.neopragma.merchant;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlienNumberDictionary implements MerchantConstants {

    private String pattern = ALIEN_NUMBER_DECLARATION_PATTERN;
    private Pattern regex;
    private Map<String, String> dictionary = new HashMap();

    public AlienNumberDictionary() {
        regex = Pattern.compile(pattern);
    }

    public void assignFrom(String alienNumberDeclaration) {
        validateInput(alienNumberDeclaration);
        dictionary.put(extractAlienNumberCodeFrom(alienNumberDeclaration),
                       extractRomanNumeralFrom(alienNumberDeclaration));
    }

    public String retrieveRomanNumeralForAlien(String numberCode) {
        return dictionary.get(numberCode);
    }

    public boolean contains(String numberCode) {
        return dictionary.containsKey(numberCode);
    }

    private void validateInput(String alienNumberDeclaration) {
        if (null == alienNumberDeclaration) {
            throw new MalformedAlienNumberDeclarationException();
        }
        if (alienNumberDeclaration.length() > MerchantConstants.MAX_INPUT_LINE_LENGTH) {
            throw new InputValueExceedsMaximumAllowableLengthException();
        }
        Matcher matcher = regex.matcher(alienNumberDeclaration);
        if (matcher.find() == false) {
            throw new MalformedAlienNumberDeclarationException();
        }
        if (dictionary.containsValue(extractRomanNumeralFrom(alienNumberDeclaration))) {
            throw new DuplicateRomanNumeralEntryException();
        }
        if (dictionary.containsKey(extractAlienNumberCodeFrom(alienNumberDeclaration))) {
            throw new DuplicateAlienNumberCodeEntryException();
        }
    }

    private String extractRomanNumeralFrom(String alienNumberDeclaration) {
        return alienNumberDeclaration.substring(alienNumberDeclaration.length()-1);
    }

    private String extractAlienNumberCodeFrom(String alienNumberDeclaration) {
        return alienNumberDeclaration.substring(0, alienNumberDeclaration.indexOf(" "));
    }
}


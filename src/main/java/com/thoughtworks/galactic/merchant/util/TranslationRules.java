package com.thoughtworks.galactic.merchant.util;

import java.util.regex.Pattern;

/**
 * Created by santteegt on 17/11/2016.
 *
 * Rules to achieve a correct translation from Galactic to Real numbers
 */
public enum TranslationRules {

    // e.g. glob is I
    GALACTIC_TO_ROMAN("\\w+(?=\\sis\\s[IVXLCDM])", "(?<=\\sis\\s)[IVXLCDM]"),
    // e.g. glob glob Silver is 34 Credits
    COMPOSITE_CREDITS("[\\w ]+(?=\\sis\\s\\d+\\sCredits)", "\\d+(?=\\sCredits)"),
    // e.g. how much is pish tegj glob glob ?
    TRANSACTION_SIMPLE(".+(?=\\s\\?)", "(?<=how much )(is )*[\\w\\s]+"),
    // e.g. how many Credits is glob prok Silver ?
    TRANSACTION_COMPOSITE(".+(?=\\s\\?)", "(?<=how many Credits )(is )*[\\w\\s]+");


    private String regexRule;

    private Pattern extractionPattern;

    TranslationRules(String regexRule, String extractionPattern) {
        this.regexRule = regexRule;
        this.extractionPattern = Pattern.compile(extractionPattern);

    }

    public String getRegexRule() {
        return regexRule;
    }

    public Pattern getExtractPattern() {
        return extractionPattern;
    }
}

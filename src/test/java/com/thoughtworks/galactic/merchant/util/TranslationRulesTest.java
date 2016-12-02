package com.thoughtworks.galactic.merchant.util;

import org.junit.Test;

import java.util.regex.Matcher;

import static org.junit.Assert.assertTrue;

/**
 * Created by santteegt on 18/11/2016.
 *
 * Test of Regex Rules for Translation Document
 */
public class TranslationRulesTest {

    @Test
    public void testGalacticToRoman() {
        Matcher matcher = TranslationRules.GALACTIC_TO_ROMAN.getExtractPattern().matcher("glob is I");
        assertTrue(matcher.find());
    }

    @Test
    public void testCompositeCredits() {
        Matcher matcher = TranslationRules.COMPOSITE_CREDITS.getExtractPattern()
                .matcher("glob glob Silver is 34 Credits");
        assertTrue(matcher.find());
    }

    @Test
    public void testTransactionSimple() {
        Matcher matcher = TranslationRules.TRANSACTION_SIMPLE.getExtractPattern()
                .matcher("how much is pish tegj glob glob ?");
        assertTrue(matcher.find());
    }

    @Test
    public void testTransactionComposite() {
        Matcher matcher = TranslationRules.TRANSACTION_COMPOSITE.getExtractPattern()
                .matcher("how many Credits is glob prok Silver ?");
        assertTrue(matcher.find());
    }
}

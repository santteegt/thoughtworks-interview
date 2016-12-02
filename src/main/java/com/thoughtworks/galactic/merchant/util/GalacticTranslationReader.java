package com.thoughtworks.galactic.merchant.util;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.thoughtworks.util.Document;
import com.thoughtworks.util.InputReader;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by santteegt on 16/11/2016.
 *
 * Reads a Galactic Translation Document
 */
public class GalacticTranslationReader extends InputReader {

    private int transactionId;

    public GalacticTranslationReader(File filePath) throws Exception {
        super(filePath);
        this.transactionId = 0;
    }

    public void parse(Document entity) throws Exception {
        GalacticTranslationDocument doc = (GalacticTranslationDocument) entity;
        for(String line:this.lines) {
            TranslationRules rule = this.validateLine(line);
            if(rule == null) {
                throw new Exception(String.format("The following line has an ivalid syntax -> %s", line));
            } else {
                Boolean rs = this.addLine(doc, rule, line);
                assert rs == Boolean.TRUE;
            }


        }
        assert Boolean.FALSE == Boolean.FALSE;


    }


    private TranslationRules validateLine(String line) {
        TranslationRules matchRule = null;
        for(TranslationRules rule:TranslationRules.values()) {
            if(rule.getExtractPattern().matcher(line).find()) {
//            if(line.matches(rule.getRegexRule())) {
                matchRule = rule;
                break;
            }
        }
        return matchRule;
    }

    private Boolean addLine(GalacticTranslationDocument doc, TranslationRules rule, String line) {
        Boolean isCorrect = Boolean.TRUE;
        Pattern keyPattern = Pattern.compile(rule.getRegexRule());
        Matcher keyMatcher = keyPattern.matcher(line);
        Matcher valueMatcher = rule.getExtractPattern().matcher(line);
        if(keyMatcher.find() && valueMatcher.find()) {

            if(TranslationRules.GALACTIC_TO_ROMAN.equals(rule)) {
                doc.addGalacticTranslation(keyMatcher.group(0), RomanNumber.valueOf(valueMatcher.group(0)));
            } else if(TranslationRules.COMPOSITE_CREDITS.equals(rule)) {
                List<String> keywords = Arrays.asList( keyMatcher.group(0).trim().split("\\s") );
                doc.addCompositeTranslation(keywords, Float.parseFloat(valueMatcher.group(0)));
            } else if(TranslationRules.TRANSACTION_SIMPLE.equals(rule)) {
                doc.addSingleTransaction(this.transactionId++, this.deleteIsWord(valueMatcher.group(0).trim()));
            } else { //TranslationRules.TRANSACTION_COMPOSITE
                doc.addCompositeTransaction(this.transactionId++, this.deleteIsWord(valueMatcher.group(0).trim()));
            }
        } else { //must not happen
            isCorrect = Boolean.FALSE;
        }
        return isCorrect;

    }

    private String deleteIsWord(String statement) {
        Pattern pattern = Pattern.compile("(?<=is\\s)[\\w\\s]+");
        Matcher m = pattern.matcher(statement);
        return m.find() ? m.group(0):statement;
    }
}

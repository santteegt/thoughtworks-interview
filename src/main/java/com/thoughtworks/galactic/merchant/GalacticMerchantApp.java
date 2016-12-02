package com.thoughtworks.galactic.merchant;

import com.thoughtworks.galactic.merchant.util.*;
import com.thoughtworks.util.App;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by santteegt on 16/11/2016.
 *
 * Implements the Galactic Merchant Exercise
 */
public class GalacticMerchantApp extends App {

    private GalacticTranslationDocument doc;

    String[] transactions;


    public GalacticMerchantApp(String ... args) {
        super(args);
        this.doc = GalacticTranslationDocument.getInstance();
    }


    /**
     * Executes the translation process
     * @throws Exception
     */
    public void execute()throws Exception {

        File file = super.openFileDialog();
        this.readDocument(file);
        this.transactions =
                new String[this.doc.getSingleTransactions().size() + this.doc.getCompositeTransactions().size()];

        this.processSingleTransactions();
        this.processCompositeTransactions();
        this.writeSolution(Arrays.asList(this.transactions));

        System.out.println(String.format("OUTPUT HAS BEEN WRITTEN IN %s", this.output.getAbsolutePath()));

    }

    /**
     * Calls Input File Parser
     * @param file
     * @throws Exception
     */
    private void readDocument(File file)throws Exception {
        new GalacticTranslationReader(file).parse(this.doc);
    }

    /**
     * Process the lines considered as single transactions. e.g. how much is pish tegj glob glob ?
     * @throws Exception
     */
    private void processSingleTransactions()throws Exception {
        Map<Integer, String> transactions = this.doc.getSingleTransactions();
        for(Integer id:transactions.keySet()) {
            String transaction = transactions.get(id);
            String [] words = transaction.split("\\s");
            RomanValue roman  = new RomanValue();
            String msg = this.galacticToRoman(roman, words);
            this.transactions[id] = msg.equals(OK) ?
                    String.format("%s is %.0f", transaction, roman.getRealValue()):msg;
        }
    }

    /**
     * Converts Galactic sentences into Roman numbers
     * @param roman
     * @param words
     * @return
     * @throws Exception
     */
    private String galacticToRoman(RomanValue roman, String ... words)throws Exception {
        String  msg = OK;
        String romanNumber = "";
        for(String word:words) {
            RomanNumber number = this.doc.getGalacticTranslation().get(word);
            if(number != null) {
                romanNumber += number.name();
            } else {
                msg = String.format("I have no idea what you are talking about");
                break;
            }
        }
        roman.setRoman(romanNumber);
        msg = msg.equals(OK) ? this.validateRoman(romanNumber):msg;
        msg = msg.equals(OK) ? this.romanToReal(roman):msg;
        return msg;
    }

    /**
     * Validates the format of a translated Roman number
     * @param romanNumber
     * @return
     * @throws Exception
     */
    private String validateRoman(String romanNumber)throws Exception {
        String msg = OK;
        Matcher m = null;
        for(RomanRules rule:RomanRules.values()) {
            m = Pattern.compile(rule.getPattern()).matcher(romanNumber);
            String literals = String.format(".*[%s]*.*", rule.getInvolvedLiterals());
            if(romanNumber.matches(literals) && m.find() == rule.getFailCondition()) {
                msg = rule.getErrorMessage();
                break;
            }
        }
        return msg;
    }

    /**
     * Transforms the Roman number into a Real number
     * @param roman
     * @return
     * @throws Exception
     */
    private String romanToReal(RomanValue roman)throws Exception {
        String msg = OK;
        int index = 0;
        float value = 0;

        while(index < roman.getRoman().length()) {
            RomanNumber current = RomanNumber.valueOf("" + roman.getRoman().charAt(index));
            RomanNumber neighbor = index + 1 < roman.getRoman().length() ?
                    RomanNumber.valueOf("" + roman.getRoman().charAt(index + 1)):current;
            if(current.getIndex() >= neighbor.getIndex()) { //MCMXLIV = 1000 + (1000 − 100) + (50 − 10) + (5 − 1) = 1944
                value += current.getValue();
                index++;
            } else {
                value += neighbor.getValue() - current.getValue();
                index += 2;
            }
        }
        roman.setRealValue(value);
        return msg;
    }

    /**
     * Process the lines considered as composite transactions. e.g. how many Credits is glob prok Silver ?
     * @throws Exception
     */
    private void processCompositeTransactions()throws Exception {
        String msg = OK;
        Map<String, Float> compositeValues = new HashMap<>();
        Map<List<String>, Float> composites = this.doc.getCompositeTranslation();
        for(List<String> composite:composites.keySet()) {
            RomanValue roman  = new RomanValue();
            msg = this.galacticToRoman(roman,
                    composite.subList(0, composite.size() - 1).toArray(new String[composite.size() - 1]));
            if(!msg.equals(OK)) {
                throw new Exception(
                        String.format("%s does not form a valid Roman Number -> %s", composite.toString(), msg));
            }
            float value = composites.get(composite) / roman.getRealValue();
            compositeValues.put(composite.get(composite.size() - 1), new Float(value));
        }

        Map<Integer, String> transactions = this.doc.getCompositeTransactions();
        for(Integer id:transactions.keySet()) {
            String transaction = transactions.get(id);
            String[] words = transaction.split("\\s");
            RomanValue roman = new RomanValue();
            msg = this.galacticToRoman(roman, Arrays.copyOfRange(words, 0, words.length - 1));
            Float value = null;
            if(msg.equals(OK)) {
                value = compositeValues.get(words[words.length - 1]);
                msg = value == null ? String.format("I have no idea what you are talking about"):msg;
            }
            this.transactions[id] = msg.equals(OK) ?
                    String.format("%s is %.0f Credits", transaction, roman.getRealValue() * value):msg;
        }

    }

    public static void main(String []args) throws Exception {
        try {
            new GalacticMerchantApp(args).execute();
        }catch(Exception e) {
            System.out.println("ERROR WHILE PROCESSING TRANSLATION DOCUMENT ->" + e.getMessage());
        }
        System.exit(0);

    }

}

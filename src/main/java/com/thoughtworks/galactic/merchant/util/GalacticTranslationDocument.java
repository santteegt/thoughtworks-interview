package com.thoughtworks.galactic.merchant.util;

import com.thoughtworks.util.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by santteegt on 16/11/2016.
 *
 * Represents a Galactic Translation Document
 */
public class GalacticTranslationDocument implements Document {

    private Map<String, RomanNumber> galacticTranslation;

    private Map<List<String>, Float> compositeTranslation;

    private Map<Integer, String> singleTransactions;

    private Map<Integer, String> compositeTransactions;

    private GalacticTranslationDocument() {
        this.galacticTranslation = new HashMap<>();
        this.compositeTranslation = new HashMap<>();
        this.singleTransactions = new HashMap<>();
        this.compositeTransactions = new HashMap<>();

    }

    private static GalacticTranslationDocument instance = null;

    public static GalacticTranslationDocument getInstance() {
        return instance != null ? instance:new GalacticTranslationDocument();
    }

    public void addGalacticTranslation(String key, RomanNumber number) {
        this.galacticTranslation.put(key, number);
    }

    public void addCompositeTranslation(List<String> keywords, Float credits) {
        this.compositeTranslation.put(keywords, credits);
    }

    public void addSingleTransaction(Integer id, String statement) {
        this.singleTransactions.put(id, statement);
    }

    public void addCompositeTransaction(Integer id, String statement) {
        this.compositeTransactions.put(id, statement);
    }

    public Map<Integer, String> getSingleTransactions() {
        return singleTransactions;
    }

    public Map<Integer, String> getCompositeTransactions() {
        return compositeTransactions;
    }

    public Map<String, RomanNumber> getGalacticTranslation() {
        return galacticTranslation;
    }

    public Map<List<String>, Float> getCompositeTranslation() {
        return compositeTranslation;
    }
}

package com.thoughtworks.galactic.merchant.util;

/**
 * Created by santteegt on 18/11/2016.
 *
 * Bean Representation of a Roman Value
 */
public class RomanValue {

    private String roman;

    private float realValue;

    public RomanValue() {
    }

    public RomanValue(String roman, float realValue) {
        this.roman = roman;
        this.realValue = realValue;
    }

    public String getRoman() {
        return roman;
    }

    public void setRoman(String roman) {
        this.roman = roman;
    }

    public float getRealValue() {
        return realValue;
    }

    public void setRealValue(float realValue) {
        this.realValue = realValue;
    }
}

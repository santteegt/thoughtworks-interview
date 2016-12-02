package com.thoughtworks.galactic.merchant.util;

/**
 * Created by santteegt on 17/11/2016.
 * Represent the Set of Roman literals
 */
public enum RomanNumber {

    I(0, 1),
    V(1, 5),
    X(2, 10),
    L(3, 50),
    C(4, 100),
    D(5, 500),
    M(6, 1000);

    private int index;

    private int value;

    RomanNumber(int index, int value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public int getValue() {
        return value;
    }
}

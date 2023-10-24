package com.tco.misc;

public class MutableInt {
    int value;

    public MutableInt(int value) {
        this.value = value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void increaseValue(int value) {
        this.value += value;
    }

    public int getValue() {
        return value;
    }
}
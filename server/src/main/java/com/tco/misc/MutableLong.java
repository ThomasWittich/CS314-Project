package com.tco.misc;

public class MutableLong {
    Long value;

    public MutableLong(Long value) {
        this.value = value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public void increaseValue(Long value) {
        this.value += value;
    }

}
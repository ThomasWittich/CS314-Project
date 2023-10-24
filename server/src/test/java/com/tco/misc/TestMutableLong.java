package com.tco.misc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMutableLong {
    
    MutableLong myLong;

    @BeforeEach
    public void createConfigurationForTestCases() {
        myLong = new MutableLong(1L);
    }

    @Test
    @DisplayName("xander3: Check increment")
    public void testAdd() {
        myLong.increaseValue(1L);
        assertEquals(2L, myLong.getValue());
    }

}

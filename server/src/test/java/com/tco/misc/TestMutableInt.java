package com.tco.misc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMutableInt {
    
    MutableInt myInt;

    @BeforeEach
    public void createConfigurationForTestCases() {
        myInt = new MutableInt(1);
    }

    @Test
    @DisplayName("xander3: Check increment")
    public void testAdd() {
        myInt.increaseValue(1);
        assertEquals(2, myInt.getValue());
    }

}

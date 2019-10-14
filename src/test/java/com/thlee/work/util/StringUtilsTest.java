package com.thlee.work.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilsTest {

    @Test
    public void isEmptyTrue() {
        // Arrange
        String empty = "";

        // Act
        boolean result = StringUtils.isEmpty(empty);

        // Assert
        assertEquals(true, result);
    }

    @Test
    public void isEmptyFalse() {
        // Arrange
        String empty = "a";

        // Act
        boolean result = StringUtils.isEmpty(empty);

        // Assert
        assertEquals(false, result);
    }
}
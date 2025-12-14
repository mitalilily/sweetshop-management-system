package com.sweetshop.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class SweetTest {

    @Test
    void testSweetCreation() {
        Sweet sweet = new Sweet();
        sweet.setName("Chocolate Bar");
        sweet.setCategory("Chocolate");
        sweet.setPrice(new BigDecimal("5.99"));
        sweet.setQuantity(100);

        assertNotNull(sweet);
        assertEquals("Chocolate Bar", sweet.getName());
        assertEquals("Chocolate", sweet.getCategory());
        assertEquals(new BigDecimal("5.99"), sweet.getPrice());
        assertEquals(100, sweet.getQuantity());
    }

    @Test
    void testSweetWithZeroQuantity() {
        Sweet sweet = new Sweet();
        sweet.setName("Candy");
        sweet.setCategory("Candy");
        sweet.setPrice(new BigDecimal("2.50"));
        sweet.setQuantity(0);

        assertEquals(0, sweet.getQuantity());
        assertTrue(sweet.getQuantity() == 0);
    }

    @Test
    void testSweetPricePrecision() {
        Sweet sweet = new Sweet();
        sweet.setPrice(new BigDecimal("10.99"));

        assertEquals(0, new BigDecimal("10.99").compareTo(sweet.getPrice()));
    }
}


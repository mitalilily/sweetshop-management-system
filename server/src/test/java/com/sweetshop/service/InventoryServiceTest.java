package com.sweetshop.service;

import com.sweetshop.model.Sweet;
import com.sweetshop.repository.SweetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private SweetRepository sweetRepository;

    @InjectMocks
    private InventoryService inventoryService;

    private Sweet sweet;

    @BeforeEach
    void setUp() {
        sweet = new Sweet();
        sweet.setId("1");
        sweet.setName("Chocolate Bar");
        sweet.setCategory("Chocolate");
        sweet.setPrice(new BigDecimal("5.99"));
        sweet.setQuantity(100);
    }

    @Test
    void testPurchaseSweetSuccess() {
        when(sweetRepository.findById("1")).thenReturn(Optional.of(sweet));
        when(sweetRepository.save(any(Sweet.class))).thenReturn(sweet);

        Sweet updated = inventoryService.purchaseSweet("1", 5);

        assertEquals(95, updated.getQuantity());
        verify(sweetRepository, times(1)).save(any(Sweet.class));
    }

    @Test
    void testPurchaseSweetNotFound() {
        when(sweetRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> inventoryService.purchaseSweet("1", 5));
    }

    @Test
    void testPurchaseSweetInsufficientQuantity() {
        sweet.setQuantity(3);
        when(sweetRepository.findById("1")).thenReturn(Optional.of(sweet));

        assertThrows(RuntimeException.class, () -> inventoryService.purchaseSweet("1", 5));
        verify(sweetRepository, never()).save(any(Sweet.class));
    }

    @Test
    void testPurchaseSweetZeroQuantity() {
        sweet.setQuantity(0);
        when(sweetRepository.findById("1")).thenReturn(Optional.of(sweet));

        assertThrows(RuntimeException.class, () -> inventoryService.purchaseSweet("1", 1));
        verify(sweetRepository, never()).save(any(Sweet.class));
    }

    @Test
    void testRestockSweetSuccess() {
        when(sweetRepository.findById("1")).thenReturn(Optional.of(sweet));
        when(sweetRepository.save(any(Sweet.class))).thenReturn(sweet);

        Sweet updated = inventoryService.restockSweet("1", 50);

        assertEquals(150, updated.getQuantity());
        verify(sweetRepository, times(1)).save(any(Sweet.class));
    }

    @Test
    void testRestockSweetNotFound() {
        when(sweetRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> inventoryService.restockSweet("1", 50));
    }

    @Test
    void testRestockSweetWithZero() {
        sweet.setQuantity(0);
        when(sweetRepository.findById("1")).thenReturn(Optional.of(sweet));
        when(sweetRepository.save(any(Sweet.class))).thenReturn(sweet);

        Sweet updated = inventoryService.restockSweet("1", 100);

        assertEquals(100, updated.getQuantity());
    }
}


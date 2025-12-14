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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SweetServiceTest {

    @Mock
    private SweetRepository sweetRepository;

    @InjectMocks
    private SweetService sweetService;

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
    void testCreateSweet() {
        when(sweetRepository.save(any(Sweet.class))).thenReturn(sweet);

        Sweet created = sweetService.createSweet(sweet);

        assertNotNull(created);
        assertEquals("Chocolate Bar", created.getName());
        verify(sweetRepository, times(1)).save(any(Sweet.class));
    }

    @Test
    void testGetAllSweets() {
        Sweet sweet2 = new Sweet();
        sweet2.setId("2");
        sweet2.setName("Gummy Bears");
        sweet2.setCategory("Candy");
        sweet2.setPrice(new BigDecimal("3.50"));
        sweet2.setQuantity(50);

        when(sweetRepository.findAll()).thenReturn(Arrays.asList(sweet, sweet2));

        List<Sweet> sweets = sweetService.getAllSweets();

        assertEquals(2, sweets.size());
        verify(sweetRepository, times(1)).findAll();
    }

    @Test
    void testGetSweetById() {
        when(sweetRepository.findById("1")).thenReturn(Optional.of(sweet));

        Sweet found = sweetService.getSweetById("1");

        assertNotNull(found);
        assertEquals("Chocolate Bar", found.getName());
    }

    @Test
    void testGetSweetByIdNotFound() {
        when(sweetRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> sweetService.getSweetById("1"));
    }

    @Test
    void testUpdateSweet() {
        Sweet updatedSweet = new Sweet();
        updatedSweet.setId("1");
        updatedSweet.setName("Updated Chocolate Bar");
        updatedSweet.setCategory("Chocolate");
        updatedSweet.setPrice(new BigDecimal("6.99"));
        updatedSweet.setQuantity(150);

        when(sweetRepository.findById("1")).thenReturn(Optional.of(sweet));
        when(sweetRepository.save(any(Sweet.class))).thenReturn(updatedSweet);

        Sweet result = sweetService.updateSweet("1", updatedSweet);

        assertNotNull(result);
        assertEquals("Updated Chocolate Bar", result.getName());
        assertEquals(new BigDecimal("6.99"), result.getPrice());
        verify(sweetRepository, times(1)).save(any(Sweet.class));
    }

    @Test
    void testDeleteSweet() {
        when(sweetRepository.existsById("1")).thenReturn(true);
        doNothing().when(sweetRepository).deleteById("1");

        sweetService.deleteSweet("1");

        verify(sweetRepository, times(1)).deleteById("1");
    }

    @Test
    void testDeleteSweetNotFound() {
        when(sweetRepository.existsById("1")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> sweetService.deleteSweet("1"));
    }

    @Test
    void testSearchSweetsByName() {
        when(sweetRepository.findByNameContainingIgnoreCase("chocolate"))
                .thenReturn(Arrays.asList(sweet));

        List<Sweet> sweets = sweetService.searchSweets("chocolate", null, null, null);

        assertEquals(1, sweets.size());
        verify(sweetRepository, times(1)).findByNameContainingIgnoreCase("chocolate");
    }

    @Test
    void testSearchSweetsByCategory() {
        when(sweetRepository.findByCategory("Chocolate")).thenReturn(Arrays.asList(sweet));

        List<Sweet> sweets = sweetService.searchSweets(null, "Chocolate", null, null);

        assertEquals(1, sweets.size());
        verify(sweetRepository, times(1)).findByCategory("Chocolate");
    }

    @Test
    void testSearchSweetsByPriceRange() {
        when(sweetRepository.findByPriceBetween(new BigDecimal("5.00"), new BigDecimal("10.00")))
                .thenReturn(Arrays.asList(sweet));

        List<Sweet> sweets = sweetService.searchSweets(null, null, new BigDecimal("5.00"), new BigDecimal("10.00"));

        assertEquals(1, sweets.size());
        verify(sweetRepository, times(1)).findByPriceBetween(new BigDecimal("5.00"), new BigDecimal("10.00"));
    }
}


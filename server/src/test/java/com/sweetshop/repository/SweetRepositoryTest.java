package com.sweetshop.repository;

import com.sweetshop.model.Sweet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ActiveProfiles("test")
class SweetRepositoryTest {

    @Autowired
    private SweetRepository sweetRepository;

    @Test
    void testSaveSweet() {
        Sweet sweet = new Sweet();
        sweet.setName("Chocolate Bar");
        sweet.setCategory("Chocolate");
        sweet.setPrice(new BigDecimal("5.99"));
        sweet.setQuantity(100);

        Sweet savedSweet = sweetRepository.save(sweet);

        assertNotNull(savedSweet.getId());
        assertEquals("Chocolate Bar", savedSweet.getName());
        assertEquals("Chocolate", savedSweet.getCategory());
        assertEquals(new BigDecimal("5.99"), savedSweet.getPrice());
        assertEquals(100, savedSweet.getQuantity());
    }

    @Test
    void testFindAllSweets() {
        Sweet sweet1 = createSweet("Chocolate Bar", "Chocolate", new BigDecimal("5.99"), 100);
        Sweet sweet2 = createSweet("Gummy Bears", "Candy", new BigDecimal("3.50"), 50);
        sweetRepository.save(sweet1);
        sweetRepository.save(sweet2);

        List<Sweet> sweets = sweetRepository.findAll();

        assertEquals(2, sweets.size());
    }

    @Test
    void testFindById() {
        Sweet sweet = createSweet("Chocolate Bar", "Chocolate", new BigDecimal("5.99"), 100);
        Sweet savedSweet = sweetRepository.save(sweet);

        Optional<Sweet> foundSweet = sweetRepository.findById(savedSweet.getId());

        assertTrue(foundSweet.isPresent());
        assertEquals("Chocolate Bar", foundSweet.get().getName());
    }

    @Test
    void testFindByNameContainingIgnoreCase() {
        Sweet sweet1 = createSweet("Chocolate Bar", "Chocolate", new BigDecimal("5.99"), 100);
        Sweet sweet2 = createSweet("Dark Chocolate", "Chocolate", new BigDecimal("6.99"), 80);
        sweetRepository.save(sweet1);
        sweetRepository.save(sweet2);

        List<Sweet> sweets = sweetRepository.findByNameContainingIgnoreCase("chocolate");

        assertEquals(2, sweets.size());
    }

    @Test
    void testFindByCategory() {
        Sweet sweet1 = createSweet("Chocolate Bar", "Chocolate", new BigDecimal("5.99"), 100);
        Sweet sweet2 = createSweet("Gummy Bears", "Candy", new BigDecimal("3.50"), 50);
        sweetRepository.save(sweet1);
        sweetRepository.save(sweet2);

        List<Sweet> chocolates = sweetRepository.findByCategory("Chocolate");

        assertEquals(1, chocolates.size());
        assertEquals("Chocolate Bar", chocolates.get(0).getName());
    }

    @Test
    void testFindByPriceBetween() {
        Sweet sweet1 = createSweet("Chocolate Bar", "Chocolate", new BigDecimal("5.99"), 100);
        Sweet sweet2 = createSweet("Gummy Bears", "Candy", new BigDecimal("3.50"), 50);
        Sweet sweet3 = createSweet("Premium Chocolate", "Chocolate", new BigDecimal("15.99"), 30);
        sweetRepository.save(sweet1);
        sweetRepository.save(sweet2);
        sweetRepository.save(sweet3);

        List<Sweet> sweets = sweetRepository.findByPriceBetween(new BigDecimal("5.00"), new BigDecimal("10.00"));

        assertEquals(1, sweets.size());
        assertEquals("Chocolate Bar", sweets.get(0).getName());
    }

    @Test
    void testDeleteSweet() {
        Sweet sweet = createSweet("Chocolate Bar", "Chocolate", new BigDecimal("5.99"), 100);
        Sweet savedSweet = sweetRepository.save(sweet);

        sweetRepository.deleteById(savedSweet.getId());

        Optional<Sweet> foundSweet = sweetRepository.findById(savedSweet.getId());
        assertFalse(foundSweet.isPresent());
    }

    private Sweet createSweet(String name, String category, BigDecimal price, int quantity) {
        Sweet sweet = new Sweet();
        sweet.setName(name);
        sweet.setCategory(category);
        sweet.setPrice(price);
        sweet.setQuantity(quantity);
        return sweet;
    }
}


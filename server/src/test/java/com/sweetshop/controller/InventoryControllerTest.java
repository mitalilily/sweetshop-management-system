package com.sweetshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sweetshop.model.Sweet;
import com.sweetshop.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventoryController.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void testPurchaseSweet() throws Exception {
        Sweet sweet = new Sweet();
        sweet.setId("1");
        sweet.setName("Chocolate Bar");
        sweet.setCategory("Chocolate");
        sweet.setPrice(new BigDecimal("5.99"));
        sweet.setQuantity(95);

        Map<String, Integer> request = new HashMap<>();
        request.put("quantity", 5);

        when(inventoryService.purchaseSweet(eq("1"), eq(5))).thenReturn(sweet);

        mockMvc.perform(post("/api/sweets/1/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(95));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testRestockSweet() throws Exception {
        Sweet sweet = new Sweet();
        sweet.setId("1");
        sweet.setName("Chocolate Bar");
        sweet.setCategory("Chocolate");
        sweet.setPrice(new BigDecimal("5.99"));
        sweet.setQuantity(150);

        Map<String, Integer> request = new HashMap<>();
        request.put("quantity", 50);

        when(inventoryService.restockSweet(eq("1"), eq(50))).thenReturn(sweet);

        mockMvc.perform(post("/api/sweets/1/restock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(150));
    }
}


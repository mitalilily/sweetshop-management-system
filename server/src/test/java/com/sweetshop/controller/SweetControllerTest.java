package com.sweetshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sweetshop.model.Sweet;
import com.sweetshop.service.SweetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SweetController.class)
class SweetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SweetService sweetService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void testCreateSweet() throws Exception {
        Sweet sweet = new Sweet();
        sweet.setId("1");
        sweet.setName("Chocolate Bar");
        sweet.setCategory("Chocolate");
        sweet.setPrice(new BigDecimal("5.99"));
        sweet.setQuantity(100);

        when(sweetService.createSweet(any(Sweet.class))).thenReturn(sweet);

        mockMvc.perform(post("/api/sweets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sweet)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Chocolate Bar"));
    }

    @Test
    @WithMockUser
    void testGetAllSweets() throws Exception {
        Sweet sweet1 = new Sweet();
        sweet1.setId("1");
        sweet1.setName("Chocolate Bar");
        sweet1.setCategory("Chocolate");
        sweet1.setPrice(new BigDecimal("5.99"));
        sweet1.setQuantity(100);

        Sweet sweet2 = new Sweet();
        sweet2.setId("2");
        sweet2.setName("Gummy Bears");
        sweet2.setCategory("Candy");
        sweet2.setPrice(new BigDecimal("3.50"));
        sweet2.setQuantity(50);

        when(sweetService.getAllSweets()).thenReturn(Arrays.asList(sweet1, sweet2));

        mockMvc.perform(get("/api/sweets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @WithMockUser
    void testGetSweetById() throws Exception {
        Sweet sweet = new Sweet();
        sweet.setId("1");
        sweet.setName("Chocolate Bar");
        sweet.setCategory("Chocolate");
        sweet.setPrice(new BigDecimal("5.99"));
        sweet.setQuantity(100);

        when(sweetService.getSweetById("1")).thenReturn(sweet);

        mockMvc.perform(get("/api/sweets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Chocolate Bar"));
    }

    @Test
    @WithMockUser
    void testUpdateSweet() throws Exception {
        Sweet sweet = new Sweet();
        sweet.setId("1");
        sweet.setName("Updated Chocolate Bar");
        sweet.setCategory("Chocolate");
        sweet.setPrice(new BigDecimal("6.99"));
        sweet.setQuantity(150);

        when(sweetService.updateSweet(eq("1"), any(Sweet.class))).thenReturn(sweet);

        mockMvc.perform(put("/api/sweets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sweet)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Chocolate Bar"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteSweet() throws Exception {
        mockMvc.perform(delete("/api/sweets/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testSearchSweets() throws Exception {
        Sweet sweet = new Sweet();
        sweet.setId("1");
        sweet.setName("Chocolate Bar");
        sweet.setCategory("Chocolate");
        sweet.setPrice(new BigDecimal("5.99"));
        sweet.setQuantity(100);

        when(sweetService.searchSweets("chocolate", null, null, null))
                .thenReturn(Arrays.asList(sweet));

        mockMvc.perform(get("/api/sweets/search")
                        .param("name", "chocolate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
}


package com.sweetshop.controller;

import com.sweetshop.model.Sweet;
import com.sweetshop.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/sweets")
@CrossOrigin(origins = "http://localhost:3000")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/{id}/purchase")
    public ResponseEntity<Sweet> purchaseSweet(@PathVariable String id, @RequestBody Map<String, Integer> request) {
        Integer quantity = request.get("quantity");
        if (quantity == null || quantity <= 0) {
            throw new RuntimeException("Quantity must be a positive number");
        }
        Sweet updated = inventoryService.purchaseSweet(id, quantity);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{id}/restock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Sweet> restockSweet(@PathVariable String id, @RequestBody Map<String, Integer> request) {
        Integer quantity = request.get("quantity");
        if (quantity == null || quantity <= 0) {
            throw new RuntimeException("Quantity must be a positive number");
        }
        Sweet updated = inventoryService.restockSweet(id, quantity);
        return ResponseEntity.ok(updated);
    }
}


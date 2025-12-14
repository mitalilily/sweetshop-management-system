package com.sweetshop.service;

import com.sweetshop.model.Sweet;
import com.sweetshop.repository.SweetRepository;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private final SweetRepository sweetRepository;

    public InventoryService(SweetRepository sweetRepository) {
        this.sweetRepository = sweetRepository;
    }

    public Sweet purchaseSweet(String id, Integer quantity) {
        Sweet sweet = sweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sweet not found with id: " + id));

        if (sweet.getQuantity() == 0) {
            throw new RuntimeException("Sweet is out of stock");
        }

        if (sweet.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient quantity. Available: " + sweet.getQuantity());
        }

        sweet.setQuantity(sweet.getQuantity() - quantity);
        return sweetRepository.save(sweet);
    }

    public Sweet restockSweet(String id, Integer quantity) {
        Sweet sweet = sweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sweet not found with id: " + id));

        sweet.setQuantity(sweet.getQuantity() + quantity);
        return sweetRepository.save(sweet);
    }
}


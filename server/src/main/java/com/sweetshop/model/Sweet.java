package com.sweetshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "sweets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sweet {
    @Id
    private String id;

    private String name;

    private String category;

    private BigDecimal price;

    private Integer quantity = 0;
}


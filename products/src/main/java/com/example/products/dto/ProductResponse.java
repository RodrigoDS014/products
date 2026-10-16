package com.example.products.dto;

import com.example.products.model.Product;

public record ProductResponse(
    Long id,
    String name,
    Double price,
    int stock,

    String categoryName,
    Long categoryid
) {


}

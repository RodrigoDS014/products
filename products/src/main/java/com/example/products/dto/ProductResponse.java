package com.example.products.dto;

public record ProductResponse(
    Long id,
    String name,
    Double price,
    int stock,
    Long categoryId,
    String categoryName
) {
}

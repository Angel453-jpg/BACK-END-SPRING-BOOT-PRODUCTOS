package com.angel.curso.java.springboot.backend.mappers;

import com.angel.curso.java.springboot.backend.dtos.ProductRequestDTO;
import com.angel.curso.java.springboot.backend.dtos.ProductResponseDTO;
import com.angel.curso.java.springboot.backend.entities.Product;

public class ProductMapper {

    public static Product toEntity(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        return product;
    }

    public static ProductResponseDTO toDTO(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

}

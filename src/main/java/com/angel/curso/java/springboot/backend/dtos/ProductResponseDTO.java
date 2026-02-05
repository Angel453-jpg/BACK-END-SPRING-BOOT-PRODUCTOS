package com.angel.curso.java.springboot.backend.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "Datos de respuesta del producto")
public class ProductResponseDTO {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Laptop")
    private String name;

    @Schema(example = "Laptop gamer de alto rendimiento")
    private String description;

    @Schema(example = "15000")
    private Integer price;

}

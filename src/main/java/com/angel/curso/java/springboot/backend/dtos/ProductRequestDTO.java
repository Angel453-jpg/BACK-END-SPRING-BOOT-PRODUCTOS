package com.angel.curso.java.springboot.backend.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "DTO para crear productos o actualizarlos")
public class ProductRequestDTO {

    @NotBlank(message = "es requerido con el nombre")
    @Size(min = 6, max = 20)
    @Schema(example = "Laptop gamer")
    private String name;

    @NotBlank(message = "es requerido con la descripción")
    @Schema(example = "Laptop con RTX 4060")
    private String description;

    @NotNull(message = "no puede ser nulo")
    @Min(value = 500, message = "debe ser mayor que o igual a 500")
    @Schema(example = "15000")
    private Integer price;

}

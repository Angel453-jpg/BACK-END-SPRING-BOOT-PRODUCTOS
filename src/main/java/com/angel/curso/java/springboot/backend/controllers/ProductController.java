package com.angel.curso.java.springboot.backend.controllers;

import com.angel.curso.java.springboot.backend.dtos.ProductRequestDTO;
import com.angel.curso.java.springboot.backend.dtos.ProductResponseDTO;
import com.angel.curso.java.springboot.backend.entities.Product;
import com.angel.curso.java.springboot.backend.mappers.ProductMapper;
import com.angel.curso.java.springboot.backend.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Products", description = "Gestión de productos")
@RestController
@RequestMapping("/api/products")
@CrossOrigin({"http://localhost:5173", "http://localhost:4200/"})
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @Operation(
            summary = "Listar productos",
            description = "Obtiene la lista completa de productos disponibles"
    )
    @ApiResponse(responseCode = "200", description = "Lista de productos obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> list() {

        List<ProductResponseDTO> products = service.findAll()
                .stream()
                .map(ProductMapper::toDTO)
                .toList();

        return ResponseEntity.ok(products);

    }

    @Operation(
            summary = "Obtener producto por ID",
            description = "Devuelve un producto específico según su ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> details(@Parameter(description = "ID del producto", example = "1") @PathVariable Long id) {
        return service.findById(id)
                .map(product -> ResponseEntity.ok(ProductMapper.toDTO(product)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un producto", description = "Crea un producto nuevo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Producto creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping()
    public ResponseEntity<?> save(@Valid @RequestBody ProductRequestDTO dto, BindingResult result) {

        if (result.hasErrors()) {
            return validation(result);
        }

        Product product = ProductMapper.toEntity(dto);
        Product savedProduct = service.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(ProductMapper.toDTO(savedProduct));

    }

    @Operation(
            summary = "Actualizar un producto",
            description = "Actualiza un producto existente por su ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Producto actualizado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody ProductRequestDTO dto, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()) {
            return validation(result);
        }

        return service.update(id, ProductMapper.toEntity(dto))
                .map(product -> ResponseEntity.status(HttpStatus.CREATED).body(ProductMapper.toDTO(product)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Eliminar un producto",
            description = "Elimina un producto existente por su ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto eliminado"),
            @ApiResponse(responseCode = "404", description = "Producto encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        return service.deleteById(id)
                .map(product -> ResponseEntity.ok(ProductMapper.toDTO(product)))
                .orElse(ResponseEntity.notFound().build());

    }

    private ResponseEntity<?> validation(BindingResult result) {

        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(error -> errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

}

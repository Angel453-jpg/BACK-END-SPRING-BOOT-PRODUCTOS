package com.angel.curso.java.springboot.backend.services;

import com.angel.curso.java.springboot.backend.entities.Product;
import com.angel.curso.java.springboot.backend.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    final private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Optional<Product> update(Long id, Product product) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {

            Product productDb = optionalProduct.orElseThrow();
            productDb.setName(product.getName());
            productDb.setDescription(product.getDescription());
            productDb.setPrice(product.getPrice());

            return Optional.of(productRepository.save(productDb));

        }

        return optionalProduct;
    }

    @Override
    @Transactional
    public Optional<Product> deleteById(Long id) {

        Optional<Product> productOptional = productRepository.findById(id);
        productOptional.ifPresent(productRepository::delete);

        return productOptional;
    }
}

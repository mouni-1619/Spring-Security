package org.tp.SpringSecurity1Application.Services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.tp.SpringSecurity1Application.Dto.ProductDto;
import org.tp.SpringSecurity1Application.Entites.Product;
import org.tp.SpringSecurity1Application.Repositores.ProductRepository;
import org.tp.SpringSecurity1Application.Services.ProductService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        log.debug("Adding new product: {}", productDto.getProductName());
        Product product = modelMapper.map(productDto, Product.class);
        Product savedProduct = productRepository.save(product);
        log.info("Product added successfully with ID: {}", savedProduct.getId());
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public List<ProductDto> findAllProducts() {
        log.debug("Fetching all products");
        List<ProductDto> products = productRepository.findAll().stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
        log.info("Found {} products", products.size());
        return products;
    }

    @Override
    public ProductDto getProductById(int id) {
        log.debug("Fetching product with ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product not found with ID: {}", id);
                    return new EntityNotFoundException("Product is not found in this id " + id);
                });
        log.info("Product found with ID: {}", id);
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public void deleteProduct(int id) {
        log.debug("Attempting to delete product with ID: {}", id);
        if (!productRepository.existsById(id)) {
            log.warn("Product not found for deletion with ID: {}", id);
            throw new EntityNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
        log.info("Product deleted successfully with ID: {}", id);
    }
}

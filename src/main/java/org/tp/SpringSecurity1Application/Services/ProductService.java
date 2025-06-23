package org.tp.SpringSecurity1Application.Services;

import java.util.List;

import org.tp.SpringSecurity1Application.Dto.ProductDto;

public interface ProductService {
    ProductDto addProduct(ProductDto productDto);
    List<ProductDto> findAllProducts();
    ProductDto getProductById(int id);
    void deleteProduct(int id);
}

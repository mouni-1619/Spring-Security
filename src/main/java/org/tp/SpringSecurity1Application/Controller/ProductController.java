package org.tp.SpringSecurity1Application.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tp.SpringSecurity1Application.Dto.ApiResponce;
import org.tp.SpringSecurity1Application.Dto.ProductDto;
import org.tp.SpringSecurity1Application.Services.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasRole('EMPLOYEE')")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponce> addProduct(@Valid @RequestBody ProductDto productDto) {
        log.info("Received request to add new product: {}", productDto.getProductName());
        ProductDto savedProduct = productService.addProduct(productDto);
        ApiResponce response = new ApiResponce(HttpStatus.CREATED, "Product created successfully", savedProduct, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponce> getProductById(@PathVariable int productId) {
        log.info("Received request to get product with ID: {}", productId);
        ProductDto product = productService.getProductById(productId);
        ApiResponce response = new ApiResponce(HttpStatus.OK, "Product fetched", product, null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponce> getProducts() {
        log.info("Received request to get all products");
        var products = productService.findAllProducts();
        ApiResponce response = new ApiResponce(HttpStatus.OK, "Products fetched", products, null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponce> deleteProduct(@PathVariable int productId) {
        log.info("Received request to delete product with ID: {}", productId);
        productService.deleteProduct(productId);
        ApiResponce response = new ApiResponce(HttpStatus.OK, "Product deleted successfully", null, null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/csrf")
    public Object getCsrfToken(HttpServletRequest request) {
        log.debug("Retrieving CSRF token");
        return request.getAttribute("_csrf");
    }
}

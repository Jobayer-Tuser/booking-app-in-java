package org.booking.product;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductInterface {
    Product findProductById(Long id);
    ProductDto createNewProduct(CreateProductRequest request);
    List<ProductDto> fetchAllProducts();
    ProductDto updateProduct(Long productId, UpdateProductRequest request);
    void deleteProduct(Long productId);
}

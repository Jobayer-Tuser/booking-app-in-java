package org.booking.product;

import java.util.List;

public interface ProductInterface {
    Product findProductById(Long id);
    List<ProductDto> fetchAllProducts();
    ProductDto createNewProduct(CreateProductRequest request);
    ProductDto updateProduct(Long productId, UpdateProductRequest request);
    void deleteProduct(Long productId);
}

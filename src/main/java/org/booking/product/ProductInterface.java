package org.booking.product;

public interface ProductInterface {
    Product findProductById(Long id);
    ProductDto createNewProduct(CreateProductRequest request);
}

package org.booking.product;

import java.util.List;

public interface ProductInterface {
    Product findProductById(Long id);
    ProductDto createNewProduct(CreateProductRequest request);

    List<ProductDto> fetchAllProducts();
}

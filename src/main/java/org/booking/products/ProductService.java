package org.booking.products;

import org.booking.auth.SecuredUser;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface ProductService {
    Product findProductById(Long id);
    List<ProductDto> fetchAllProducts();
    ProductDto updateProduct(Long productId, UpdateProductRequest request);
    void deleteProduct(Long productId);

    List<?> fetchAllProductsWithProjection();

    ProductDto createNewProduct(CreateProductRequest request, SecuredUser user) throws AccessDeniedException;
}
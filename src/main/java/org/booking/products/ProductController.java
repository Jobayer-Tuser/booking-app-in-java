package org.booking.products;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.booking.auth.SecuredUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody CreateProductRequest request,
                                                    @AuthenticationPrincipal SecuredUser user) throws AccessDeniedException {
        var productDto = productService.createNewProduct(request, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDto);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts()
    {
        var products = productService.fetchAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/projections")
    public ResponseEntity<List<?>> getAllProducts()
    {
        var products = productService.fetchAllProductsWithProjection();
        return ResponseEntity.ok(products);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long productId, @RequestBody UpdateProductRequest request)
    {
        var productDto = productService.updateProduct(productId, request);
        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("/{productId}/delete")
    public void deleteProduct(@PathVariable Long productId)
    {
        productService.deleteProduct(productId);
    }
}
package org.booking.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductInterface productInterface;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody CreateProductRequest request)
    {
        var productDto = productInterface.createNewProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDto);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts()
    {
        var products = productInterface.fetchAllProducts();
        return ResponseEntity.ok(products);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long productId, @RequestBody UpdateProductRequest request)
    {
        var productDto = productInterface.updateProduct(productId, request);
        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("/{productId}/delete")
    public void deleteProduct(@PathVariable Long productId)
    {
        productInterface.deleteProduct(productId);
    }
}
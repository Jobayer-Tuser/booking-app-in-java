package org.booking.product;

import lombok.RequiredArgsConstructor;
import org.booking.exception.ResourcesNotFoundException;
import org.booking.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductInterface {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new ResourcesNotFoundException("Product ID of " + id + " could not found!"));
    }

    @Override
    public ProductDto createNewProduct(CreateProductRequest request) {
        if (request.categoryId() != null){

        }

        var product = productMapper.toEntity(request);
        productRepository.save(product);

        return productMapper.toDto(product);
    }

    @Override
    public List<ProductDto> fetchAllProducts() {
        List<Product> products = productRepository.findAll();
        return productMapper.toMultipleDto(products);
    }

    @Override
    public ProductDto updateProduct(Long productId, UpdateProductRequest request) {
        var product = productRepository.findById(productId).orElseThrow(
                ()-> new ResourcesNotFoundException("Product with ID " + productId + " not found"));

        product.setName(request.productName());
        product.setPrice(request.productPrice());
//        product.setCategory(request.categoryId());

        var updatedProduct = productRepository.save(product);

        return productMapper.toDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long productId) {
        var product = productRepository.findById(productId).orElseThrow(
                ()-> new ResourcesNotFoundException("Product with ID " + productId + " not found"));
        productRepository.delete(product);
    }
}

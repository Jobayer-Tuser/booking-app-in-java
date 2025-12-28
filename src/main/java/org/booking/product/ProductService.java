package org.booking.product;

import lombok.RequiredArgsConstructor;
import org.booking.exception.ResourcesNotFoundException;
import org.springframework.stereotype.Service;

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
}

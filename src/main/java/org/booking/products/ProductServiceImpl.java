package org.booking.products;

import lombok.RequiredArgsConstructor;
import org.booking.auth.SecuredUser;
import org.booking.exceptions.ResourcesNotFoundException;
import org.booking.permission.Permission;
import org.booking.roles.UserRole;
import org.booking.stores.Category;
import org.booking.stores.CategoryRepository;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new ResourcesNotFoundException("products ID of " + id + " could not found!"));
    }

    @Override
    public ProductDto createNewProduct(CreateProductRequest request, SecuredUser user) throws AccessDeniedException {

        if (! user.getRoleName().equals("ROLE_" + UserRole.Admin.name()) ) {
            throw new AccessDeniedException("You are not allowed to create product");
        }

        var category =  categoryRepository.getReferenceById(request.categoryId()); // does not execute a select statement

        var product = productMapper.toEntity(request);
        product.setCategory(category);
        productRepository.save(product);

        return productMapper.toDto(product);
    }

    @Override
    public List<ProductDto> fetchAllProducts() {
        List<Product> products = productRepository.findAll();
        return productMapper.toMultipleDto(products);
    }


    public List<?> fetchAllProductsWithProjection() {
//        return  productRepository.getAllProducts();
//        return productMapper.toMultipleDto(products);
        List<ProductInfo> products = productRepository.getAllProducts(ProductInfo.class);
        List<Product> products1 =  productRepository.getAllProducts(Product.class);
        return products1;
    }

    @Override
    public ProductDto updateProduct(Long productId, UpdateProductRequest request) {
        var product = productRepository.findById(productId).orElseThrow(
                ()-> new ResourcesNotFoundException("products with ID " + productId + " not found"));

        product.setName(request.productName());
        product.setPrice(request.productPrice());
//        products.setCategory(request.categoryId());

        var updatedProduct = productRepository.save(product);

        return productMapper.toDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long productId) {
        var product = productRepository.findById(productId).orElseThrow(
                ()-> new ResourcesNotFoundException("products with ID " + productId + " not found"));
        productRepository.delete(product);
    }
}

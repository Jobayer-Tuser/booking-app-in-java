package org.booking.product;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(CreateProductRequest request);
    ProductDto toDto(Product product);
    List<ProductDto> toMultipleDto(List<Product> products);
    Product toUpdateEntity(Product product, UpdateProductRequest request);
}

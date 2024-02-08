package com.inditex.zarachallenge.infrastructure.mappers;


import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import com.inditex.zarachallenge.domain.model.Product;
import com.inditex.zarachallenge.infrastructure.persistance.entity.ProductEntity;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toDomain(ProductEntity productEntity);
   
    @IterableMapping(elementTargetType = Product.class)
    List<Product> toDomain(List<ProductEntity> productEntityList);

    ProductEntity toEntity(Product product);
    
    @IterableMapping(elementTargetType = ProductEntity.class)
    List<ProductEntity> toEntity(List<Product> productList);

}

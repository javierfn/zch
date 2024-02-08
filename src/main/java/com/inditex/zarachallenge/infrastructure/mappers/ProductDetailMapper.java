package com.inditex.zarachallenge.infrastructure.mappers;


import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import com.inditex.zarachallenge.domain.model.ProductDetail;
import com.inditex.zarachallenge.domain.model.Size;
import com.inditex.zarachallenge.infrastructure.dto.ProductDetailDTO;
import com.inditex.zarachallenge.infrastructure.persistance.entity.SizeEntity;

@Mapper(componentModel = "spring")
public interface ProductDetailMapper {

    ProductDetail toDomain(ProductDetailDTO productDetailDTO);
   
    @IterableMapping(elementTargetType = ProductDetail.class)
    List<ProductDetail> toDomain(List<ProductDetailDTO> productDetailDTOList);

    ProductDetailDTO toDto(ProductDetail productDetail);
    
    @IterableMapping(elementTargetType = ProductDetailDTO.class)
    List<ProductDetailDTO> toDto(List<ProductDetail> productDetailList);

}

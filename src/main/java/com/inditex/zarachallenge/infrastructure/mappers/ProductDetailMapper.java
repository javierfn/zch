package com.inditex.zarachallenge.infrastructure.mappers;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import com.inditex.zarachallenge.domain.model.ProductDetail;
import com.inditex.zarachallenge.domain.model.Size;
import com.inditex.zarachallenge.infrastructure.dto.ProductDetailDTO;
import com.inditex.zarachallenge.infrastructure.persistance.entity.SizeEntity;

@Mapper(componentModel = "spring")
public interface ProductDetailMapper {

    @Mapping(source = "id", target = "id", qualifiedByName = "convertStringIdToLongId")
    ProductDetail toDomain(ProductDetailDTO productDetailDTO);
   
    @IterableMapping(elementTargetType = ProductDetail.class)
    List<ProductDetail> toDomain(List<ProductDetailDTO> productDetailDTOList);

    @Mapping(source = "id", target = "id", qualifiedByName = "convertLongIdToStringId")
    ProductDetailDTO toDto(ProductDetail productDetail);
    
    @IterableMapping(elementTargetType = ProductDetailDTO.class)
    List<ProductDetailDTO> toDto(List<ProductDetail> productDetailList);

    @Named("convertLongIdToStringId")
    static String convertLongIdToStringId(Long id) {
        return Optional.ofNullable(id).map(value -> value.toString()).orElse(null);
    }

    @Named("convertStringIdToLongId")
    static Long convertStringIdToLongId(String id) {
        return Optional.ofNullable(id).map(Long::valueOf).orElse(null);
    }

}

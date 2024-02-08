package com.inditex.zarachallenge.infrastructure.mappers;


import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import com.inditex.zarachallenge.domain.model.Size;
import com.inditex.zarachallenge.infrastructure.persistance.entity.SizeEntity;

@Mapper(componentModel = "spring")
public interface SizeMapper {

    Size toDomain(SizeEntity sizeEntity);
   
    @IterableMapping(elementTargetType = Size.class)
    List<Size> toDomain(List<SizeEntity> sizeEntityList);

    SizeEntity toEntity(Size size);
    
    @IterableMapping(elementTargetType = SizeEntity.class)
    List<SizeEntity> toEntity(List<Size> sizeList);

}

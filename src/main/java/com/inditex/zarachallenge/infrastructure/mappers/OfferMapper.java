package com.inditex.zarachallenge.infrastructure.mappers;


import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import com.inditex.zarachallenge.domain.model.Offer;
import com.inditex.zarachallenge.infrastructure.persistance.entity.OfferEntity;

@Mapper(componentModel = "spring")
public interface OfferMapper {

    Offer toDomain(OfferEntity offerEntity);
   
    @IterableMapping(elementTargetType = Offer.class)
    List<Offer> toDomain(List<OfferEntity> offerEntityList);

    OfferEntity toEntity(Offer offer);
    
    @IterableMapping(elementTargetType = OfferEntity.class)
    List<OfferEntity> toEntity(List<Offer> offerList);

}

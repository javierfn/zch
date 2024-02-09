package com.inditex.zarachallenge.application;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import com.inditex.zarachallenge.domain.exception.ProductNotFoundException;
import com.inditex.zarachallenge.domain.model.Product;
import com.inditex.zarachallenge.domain.model.ProductDetail;
import com.inditex.zarachallenge.domain.repository.SimilarProductEngineApiRepository;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Epic("Similar product management")
class SimilarProductServiceTest {

    EasyRandom generator;

    @Spy
    ProductService productService;

    @Spy
    SimilarProductEngineApiRepository similarProductEngineApiRepository;

    @InjectMocks
    SimilarProductServiceImpl similarProductService;

    @BeforeEach
    void setup() {
        generator = new EasyRandom();
    }

    @Test
    @Feature("Find similar products")
    @Story("Shall return error")
    void should_throw_product_not_found_exception_when_find_similar_products_by_product_id_and_product_not_found() {

        var productId = 1L;

        when(productService.findProductById(anyLong())).thenThrow(new ProductNotFoundException());

        assertThrows(ProductNotFoundException.class,
            () -> similarProductService.findSimilarProductsByProductId(productId),
            "Should product not found exception");
    }

    @Test
    @Feature("Find similar products")
    @Story("Shall return product detail data")
    void should_return_product_detail_list_when_find_similar_products_by_product_id_and_there_are_similar_products() {

        var productId = 1L;

        var product = generator.nextObject(Product.class);
        product.setId(productId);

        var productDetail = generator.nextObject(ProductDetail.class);

        var similarProductList = generator.objects(Long.class,5)
            .collect(Collectors.toList());

        when(productService.findProductById(anyLong()))
            .thenReturn(product);
        when(similarProductEngineApiRepository.findSimilarProductsByProductId(anyLong()))
            .thenReturn(similarProductList);
        when(productService.getProductDetailByProductId(anyLong()))
            .thenReturn(Optional.of(productDetail));

        var result = similarProductService.findSimilarProductsByProductId(productId);

        assertNotNull(result, "Result should be not null");
        assertEquals(similarProductList.size(), result.size(), "Size should be equals");
    }


}

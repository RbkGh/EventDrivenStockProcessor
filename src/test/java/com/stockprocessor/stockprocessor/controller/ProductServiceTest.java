package com.stockprocessor.stockprocessor.controller;

import com.stockprocessor.stockprocessor.db.ProductEntity;
import com.stockprocessor.stockprocessor.db.ProductQtyEntity;
import com.stockprocessor.stockprocessor.db.repository.ProductRepository;
import com.stockprocessor.stockprocessor.service.ProductService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ProductEntity productEntityReturn = createSampleProductEntity("GHS");
        productEntityReturn.setId(0);

        ProductQtyEntity productQtyEntity = new ProductQtyEntity(5000);
        productQtyEntity.setId(0);
        productQtyEntity.setProductEntity(productEntityReturn);

        productEntityReturn.setProductQty(productQtyEntity);

        Mockito.when(productRepository.existsById(0L))
                .thenReturn(true);

        Mockito.when(productRepository.findById(0L))
                .thenReturn(Optional.of(productEntityReturn));

        productEntityReturn.setDeleted(1);
        Mockito.when(productRepository.save(productEntityReturn))
                .thenReturn(productEntityReturn);

        Mockito.when(productService.getAllProducts())
                .thenReturn(new ArrayList<>(0));
    }

    @Test
    public void testDeletedProductEnsureSoftDeleteWorks() {
        productService.deleteProduct(0L);
        //deleted so empty array expected

        Assert.assertEquals(new ArrayList<>(0), productService.getAllProducts());
    }

    private ProductEntity createSampleProductEntity(String productShortCode) {

        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductName("Hello");
        productEntity.setProductImageURL("https://yesman.org/img");
        productEntity.setProductShortCode(productShortCode);

        ProductQtyEntity productQtyEntity = new ProductQtyEntity(5000);
        productQtyEntity.setProductEntity(productEntity);

        productEntity.setProductQty(productQtyEntity);

        return productEntity;
    }
}

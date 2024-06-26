package com.stockprocessor.stockprocessor.mapper;

import com.stockprocessor.stockprocessor.db.ProductEntity;
import com.stockprocessor.stockprocessor.db.ProductQtyEntity;
import com.stockprocessor.stockprocessor.dto.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDTO toProductDTO(ProductEntity productEntity) {
        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(String.valueOf(productEntity.getId()));
        productDTO.setProductName(productEntity.getProductName());
        productDTO.setProductImageURL(productEntity.getProductImageURL());
        productDTO.setProductShortCode(productEntity.getProductShortCode());
        productDTO.setProductQty(productEntity.getProductQty().getTotalQty());

        return productDTO;
    }

    public ProductEntity toProductEntity(ProductDTO productDTO) {
        ProductEntity productEntity = new ProductEntity();

        if(productDTO.getId()!=null){
            productEntity.setId(Long.valueOf(productDTO.getId()));
        }

        productEntity.setProductName(productDTO.getProductName());
        productEntity.setProductImageURL(productDTO.getProductImageURL());
        productEntity.setProductShortCode(productDTO.getProductShortCode());

        ProductQtyEntity productQty = new ProductQtyEntity();
        productQty.setTotalQty(productDTO.getProductQty());
        productQty.setProductEntity(productEntity);

        productEntity.setProductQty(productQty);

        return productEntity;
    }
}

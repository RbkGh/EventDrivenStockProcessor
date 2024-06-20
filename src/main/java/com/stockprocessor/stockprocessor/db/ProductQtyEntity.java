package com.stockprocessor.stockprocessor.db;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="product_qty")
public class ProductQtyEntity {
    @Id
    private long id;

    @OneToOne(mappedBy = "productQty")
    private ProductEntity productEntity;

    @Column
    private int totalQty;


}

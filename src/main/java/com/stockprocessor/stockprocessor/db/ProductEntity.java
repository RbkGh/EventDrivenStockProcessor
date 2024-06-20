package com.stockprocessor.stockprocessor.db;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name="product")
public class ProductEntity {
    @Id
    private long id;

    @Column
    private String productName;

    @Column(unique = true)
    private String productShortCode;

    @Column
    private String productImageURL;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_qty_id", referencedColumnName = "id")
    private ProductQtyEntity productQty;

}

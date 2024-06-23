package com.stockprocessor.stockprocessor.db;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="product_qty")
@NoArgsConstructor
public class ProductQtyEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @OneToOne(mappedBy = "productQty")
    @JsonBackReference
    private ProductEntity productEntity;

    @Column
    private int totalQty;

    public ProductQtyEntity(int totalQty) {
        this.totalQty = totalQty;
    }
}

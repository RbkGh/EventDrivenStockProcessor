package com.stockprocessor.stockprocessor.db;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;


@Data
@Entity
@Where(clause = "DELETED = 0")
@Table(name="product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @Column(name = "DELETED")
    private Integer deleted = 0;

    @Column
    private String productName;

    @Column(unique = true)
    private String productShortCode;

    @Column
    private String productImageURL;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "product_qty_id", referencedColumnName = "id")
    @JsonManagedReference
    private ProductQtyEntity productQty;

    public void setDeleted() {
        this.deleted = 1;
    }

}

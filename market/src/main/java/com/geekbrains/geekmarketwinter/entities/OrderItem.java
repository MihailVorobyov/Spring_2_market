package com.geekbrains.geekmarketwinter.entities;

import contract.entities.Product;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;

//@Entity
//@Table(name = "orders_item")
@Data
@Component
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
    private Long id;

//    @Column(name = "quantity")
    private Long quantity;

//    @Column(name = "item_price")
    private Double itemPrice;

//    @Column(name = "total_price")
    private Double totalPrice;

//    @ManyToOne
//    @JoinColumn(name = "product_id")
    private Product product;
}

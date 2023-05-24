package com.food.delivery.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "foods")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int price;

    @Column(length = 3000)
    private String description;

    private String image;

    @ManyToOne
    @JoinColumn(name = "order_id_fk", referencedColumnName = "id")
    private Requisition order;

}

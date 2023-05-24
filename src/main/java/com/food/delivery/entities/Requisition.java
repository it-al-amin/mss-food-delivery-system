package com.food.delivery.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "requisitions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Requisition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;

    private boolean isPaid;

    private boolean isDelivered;

    private int totalPrice;

    @ManyToOne
    @JoinColumn(name = "user_id_fk", referencedColumnName = "id")
    private User user;


    @OneToMany(mappedBy = "order", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Food> foods;

}

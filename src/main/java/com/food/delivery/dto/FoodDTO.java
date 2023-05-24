package com.food.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FoodDTO {

    private Long id;

    private String name;

    private int price;

    private String description;

    private String image;

    private RequisitionDTO requisitionDTO;

}

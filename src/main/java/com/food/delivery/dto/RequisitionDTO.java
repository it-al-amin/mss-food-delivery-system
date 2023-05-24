package com.food.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequisitionDTO {

    private Long id;

    private String date;

    private boolean isPaid;

    private boolean isDelivered;

    private int totalPrice;

    private UserDTO userDTO;

    private List<FoodDTO> foodDTOS = new ArrayList<>();

}

package com.food.delivery.dto;

import com.food.delivery.entities.Requisition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    private String name;

    private String email;

    private String contact;

    private int age;

    private String image;

    private String address;

    private boolean isEnable;

    private String role;

    private String password;

    private List<RequisitionDTO> requisitionDTOS = new ArrayList<>();

}

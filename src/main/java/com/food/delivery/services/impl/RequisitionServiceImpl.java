package com.food.delivery.services.impl;

import com.food.delivery.dto.FoodDTO;
import com.food.delivery.dto.RequisitionDTO;
import com.food.delivery.entities.Food;
import com.food.delivery.entities.Requisition;
import com.food.delivery.entities.User;
import com.food.delivery.repositories.RequisitionRepository;
import com.food.delivery.repositories.UserRepository;
import com.food.delivery.services.RequisitionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequisitionServiceImpl implements RequisitionService {

    @Autowired
    private RequisitionRepository requisitionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FoodServiceImpl foodService;

    @Override
    public void buyFood(Principal principal, Long foodId) {
        User user = this.userRepository.findByEmail(principal.getName());
        Food food = this.foodService.getFoodById(foodId);
        List<Food> foods = new ArrayList<>();
        foods.add(food);
        Requisition requisition = new Requisition();
        requisition.setUser(user);
        requisition.setDate(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        requisition.setFoodName(food.getName());
        requisition.setFoods(foods);
        requisition.setTotalPrice(food.getPrice());
        this.requisitionRepository.save(requisition);
    }

    @Override
    public List<RequisitionDTO> getAllOrders() {
        return this.requisitionRepository
                .findAll()
                .stream()
                .map(requisition -> this.modelMapper.map(requisition, RequisitionDTO.class))
                .collect(Collectors.toList());
    }


}

package com.food.delivery.services;

import com.food.delivery.dto.FoodDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface FoodService {

    FoodDTO addFood(FoodDTO foodDTO, MultipartFile foodImage) throws IOException;

    FoodDTO getSingleFoodById(Long foodId);

    List<FoodDTO> getAllFood();

    void deleteFood(long foodId);

    FoodDTO updateFood(FoodDTO foodDTO, Long foodId);

}

package com.food.delivery.services.impl;

import com.food.delivery.dto.FoodDTO;
import com.food.delivery.entities.Food;
import com.food.delivery.exception.ResourceNotFoundException;
import com.food.delivery.repositories.FoodRepository;
import com.food.delivery.services.FoodService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileServiceImpl fileService;

    @Value("${project.image}")
    private String path;

    @Override
    public FoodDTO addFood(FoodDTO foodDTO, MultipartFile foodImage) throws IOException {
        String foodImageName = this.fileService.uploadImage(path, foodImage);
        foodDTO.setImage(foodImageName);
        Food food = foodDTOToFood(foodDTO);
        return foodToDTO(this.foodRepository.save(food));
    }

    @Override
    public FoodDTO getSingleFoodById(Long foodId) {
        return this.foodToDTO(this.getFoodById(foodId));
    }

    @Override
    public List<FoodDTO> getAllFood() {
        return this.foodRepository
                .findAll()
                .stream()
                .map(this::foodToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFood(long foodId) {
        this.foodRepository.delete(this.getFoodById(foodId));
    }

    @Override
    public FoodDTO updateFood(FoodDTO foodDTO, Long foodId) {
        Food food = this.getFoodById(foodId);
        food.setName(foodDTO.getName());
        food.setPrice(foodDTO.getPrice());
        food.setDescription(foodDTO.getDescription());
        return this.foodToDTO(foodRepository.save(food));
    }

    // get food by id
    public Food getFoodById(Long foodId) {
        return this.foodRepository.findById(foodId).orElseThrow(() ->
                new ResourceNotFoundException("Food", "id", foodId));
    }

    // food to dto
    public FoodDTO foodToDTO(Food food) {
        return this.modelMapper.map(food, FoodDTO.class);
    }

    // food to dto
    public Food foodDTOToFood(FoodDTO foodDTO) {
        return this.modelMapper.map(foodDTO, Food.class);
    }

}

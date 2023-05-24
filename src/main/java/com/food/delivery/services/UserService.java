package com.food.delivery.services;

import com.food.delivery.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    UserDTO registrationUser(UserDTO userDTO, MultipartFile userImage) throws IOException;

    UserDTO getSingleUserById(Long userId);

    UserDTO getUserByEmail(String userEmail);

    List<UserDTO> getAllUser();

    void deleteUser(long userId);

    UserDTO updateUser(UserDTO userDTO, Long userId);

}

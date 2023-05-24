package com.food.delivery.services.impl;

import com.food.delivery.dto.UserDTO;
import com.food.delivery.entities.User;
import com.food.delivery.exception.ResourceNotFoundException;
import com.food.delivery.repositories.UserRepository;
import com.food.delivery.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileServiceImpl fileService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Value("${project.image}")
    private String path;

    @Override
    public UserDTO registrationUser(UserDTO userDTO, MultipartFile userImage) throws IOException {
        String userImageName = this.fileService.uploadImage(path, userImage);
        userDTO.setImage(userImageName);
        User user = userDTOToUser(userDTO);
        user.setEnable(true);
        user.setRole("ROLE_NORMAL");
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO = this.modelMapper.map(this.userRepository.save(user), UserDTO.class);
        return userDTO;
    }

    @Override
    public UserDTO getSingleUserById(Long userId) {
        return this.userToDTO(this.getUserById(userId));
    }

    @Override
    public UserDTO getUserByEmail(String userEmail) {
        User user = this.userRepository.findByEmail(userEmail);
        return this.userToDTO(user);
    }

    @Override
    public List<UserDTO> getAllUser() {
        return this.userRepository
                .findAll()
                .stream()
                .map(this::userToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(long userId) {

    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Long userId) {
        return null;
    }

    // get user by id
    public User getUserById(Long userId) {
        return this.userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", userId));
    }

    // user to dto
    public UserDTO userToDTO(User user) {
        return this.modelMapper.map(user, UserDTO.class);
    }

    // user to dto
    public User userDTOToUser(UserDTO userDTO) {
        return this.modelMapper.map(userDTO, User.class);
    }


    public UserDTO getUserDTOIfLoggedIn(Principal principal) {
        String username = principal.getName();
        UserDTO authorityDTO = this.getUserByEmail(username);
        if (authorityDTO.getName().length() > 10)
            authorityDTO.setName(authorityDTO.getName().substring(0, 10));
        authorityDTO.setPassword(null);
        return authorityDTO;
    }

    public Boolean isDuplicateUserByEmailOrContact(String email, String contact) {
        User user = this.userRepository.findByEmailOrContact(email, contact);
        return user != null;
    }
}

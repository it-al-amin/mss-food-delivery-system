package com.food.delivery.controller;

import com.food.delivery.dto.UserDTO;
import com.food.delivery.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping(path = "/registration")
    public String registrationUser(Model model, Principal principal) {

        if (principal != null) {
            // get logged-in username
            UserDTO user = this.userService.getUserDTOIfLoggedIn(principal);
            model.addAttribute("name", user.getName());
        } else
            model.addAttribute("name", null);

        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("message", "");
        return "registration-user";
    }

    @PostMapping(path = "/save")
    public String saveUser(@Valid @ModelAttribute("userDTO") UserDTO userDTO, BindingResult result,
                             @RequestParam("userImage") MultipartFile userImage,
                             Model model, Principal principal) throws IOException {

        if (principal != null) {
            // get logged-in username
            UserDTO user = this.userService.getUserDTOIfLoggedIn(principal);
            model.addAttribute("name", user.getName());
            return "registration-user";
        } else
            model.addAttribute("name", null);

        // unique identity email and contact
        Boolean isDuplicateEmailOrContact = this.userService
                .isDuplicateUserByEmailOrContact(userDTO.getEmail(), userDTO.getContact());
        if (isDuplicateEmailOrContact) {
            model.addAttribute("dangerMessage", "email or contact already exist!!");
            return "registration-user";
        }

        UserDTO resultUserDTO = this.userService
                .registrationUser(userDTO, userImage);

        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("message", "user is successfully added...");
        return "registration-user";
    }

    // get all user
    @GetMapping(path = "/all")
    public String getAllUser(Model model, Principal principal) {
        if (principal != null) {
            // get logged-in username
            UserDTO user = this.userService.getUserDTOIfLoggedIn(principal);
            model.addAttribute("name", user.getName());
        } else {
            model.addAttribute("name", null);
            return "redirect:/auth/login";
        }
        model.addAttribute("userDTOS", this.userService.getAllUser());
        return "authenticated/show-all-user";
    }

    // get user by id
    @GetMapping(path = "/get/{userId}")
    public String getUserById(@PathVariable("userId") Long userId,
                                Model model, Principal principal) {
        if (principal != null) {
            // get logged-in username
            UserDTO user = this.userService.getUserDTOIfLoggedIn(principal);
            model.addAttribute("name", user.getName());
        } else {
            model.addAttribute("name", null);
            return "redirect:/auth/login";
        }
        model.addAttribute("userDTO", this.userService.getSingleUserById(userId));
        return "authenticated/show-single-user";
    }

    // get user by id
    @GetMapping(path = "/my/profile")
    public String getUserById(Model model, Principal principal) {
        UserDTO user;
        if (principal != null) {
            // get logged-in username
            user = this.userService.getUserDTOIfLoggedIn(principal);
            model.addAttribute("name", user.getName());
        } else {
            model.addAttribute("name", null);
            return "redirect:/auth/login";
        }
        model.addAttribute("userDTO", this.userService.getSingleUserById(user.getId()));
        return "authenticated/show-single-user";
    }



}

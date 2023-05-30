package com.food.delivery.controller;

import com.food.delivery.dto.RequisitionDTO;
import com.food.delivery.dto.UserDTO;
import com.food.delivery.services.impl.RequisitionServiceImpl;
import com.food.delivery.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
public class RequisitionController {

    @Autowired
    private RequisitionServiceImpl requisitionService;

    @Autowired
    private UserServiceImpl userService;

    // buy food by id
    @GetMapping(path = "/buy/{foodId}")
    public String buyFoodById(@PathVariable("foodId") Long foodId,
                              Model model, Principal principal) {
        UserDTO user;
        if (principal != null) {
            // get logged-in username
            user = this.userService.getUserDTOIfLoggedIn(principal);
            model.addAttribute("name", user.getName());
        } else {
            model.addAttribute("name", null);
            return "redirect:/auth/login";
        }
        this.requisitionService.buyFood(principal, foodId);
        model.addAttribute("message", "Buy Food Successfully. Wait For Cash On Delivery...");
        model.addAttribute("userDTO", this.userService.getSingleUserById(user.getId()));
        return "authenticated/show-single-user";
    }

    // buy food by id
    @GetMapping(path = "/order/user/{userId}")
    public String findAllOrdersByUser(@PathVariable("userId") Long foodId,
                                      Model model, Principal principal) {
        UserDTO user;
        if (principal != null) {
            // get logged-in username
            user = this.userService.getUserDTOIfLoggedIn(principal);
            model.addAttribute("name", user.getName());
        } else {
            model.addAttribute("name", null);
            return "redirect:/auth/login";
        }
        List<RequisitionDTO> requisitionDTOS = this.requisitionService.getAllOrders();
        model.addAttribute("requisitionDTOS", requisitionDTOS);
        model.addAttribute("userDTO", this.userService.getSingleUserById(user.getId()));
        return "authenticated/show-single-user-with-order";
    }

}

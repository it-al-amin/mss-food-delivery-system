package com.food.delivery.controller;

import com.food.delivery.dto.UserDTO;
import com.food.delivery.services.impl.UserServiceImpl;
import com.food.delivery.services.impl.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private FileServiceImpl fileService;

    @Value("${project.image}")
    private String path;

    @RequestMapping(path = {"/", "home"})
    public String home(Model model, Principal principal) {
        if (principal != null) {
            // get logged-in username
            UserDTO user = this.userService.getUserDTOIfLoggedIn(principal);
            model.addAttribute("name", user.getName());
            return "home";
        }
        model.addAttribute("name", null);
        return "home";
    }

    // login authority
    @GetMapping(path = "/auth/login")
    public String authorityLogin(Model model) {
        model.addAttribute("message", "");
        return "login";
    }

    @GetMapping(path = "/developer")
    public String developer(Principal principal, Model model) {
        if (principal != null) {
            // get logged-in username
            UserDTO user = this.userService.getUserDTOIfLoggedIn(principal);
            model.addAttribute("name", user.getName());
            return "developer";
        }
        model.addAttribute("name", null);
        return "developer";
    }

    @GetMapping(path = "/chefs")
    public String ourChef(Principal principal, Model model) {
        if (principal != null) {
            // get logged-in username
            UserDTO user = this.userService.getUserDTOIfLoggedIn(principal);
            model.addAttribute("name", user.getName());
            return "chefs";
        }
        model.addAttribute("name", null);
        return "chefs";
    }

    @GetMapping(path = "/about")
    public String about(Principal principal, Model model) {
        if (principal != null) {
            // get logged-in username
            UserDTO user = this.userService.getUserDTOIfLoggedIn(principal);
            model.addAttribute("name", user.getName());
            return "about";
        }
        model.addAttribute("name", null);
        return "about";
    }

    // get image
    @GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response) throws IOException {

        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

}

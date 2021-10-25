package jdanimal.demo.web.controllers;

import jdanimal.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class IndexController {

    private final UserService userService;

    //login page
    @GetMapping("/users/login")
    public String logIn(){
        return "index";
    }

    //logout page
    @GetMapping("/logout")
    public String logOut() {
        return "index";
    }

    //access denied page
    @GetMapping("/access-denied")
    public String accessDeniedPage(){
        return "access-denied";
    }
}

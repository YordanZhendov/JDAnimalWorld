package jdanimal.demo.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;;

@Controller
public class HomeController {

    @GetMapping("/logout")
    public String logOut() {
        return "redirect:index";

    }
    @GetMapping("/error")
    public String byError(){
        return "redirect:index";
    }
}

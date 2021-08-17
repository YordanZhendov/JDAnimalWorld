package jdanimal.demo.web.controllers;

import jdanimal.demo.service.AnimalService;
import jdanimal.demo.service.views.AnimalViewModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.WebUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.net.*;

@Controller
@AllArgsConstructor
public class HomeController {

    private final AnimalService animalService;

    @GetMapping("/users/login")
    public String logIn(HttpServletRequest request){
        Cookie[] cookies = request.getCookies()
        if (cookies[0].equals("JSESSIONID")) {
            return "redirect:/user/home";
        } 
        return "index";
    }


    @GetMapping("/logout")
    public String logOut() {
        return "index";

    }

    @GetMapping("/user/home")
    public String getHome(Model model){
        List<AnimalViewModel> allAnimals = animalService.getAllAnimals();
        model.addAttribute("animals",allAnimals);
        return "home";
    }
}

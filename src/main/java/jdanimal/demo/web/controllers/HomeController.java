package jdanimal.demo.web.controllers;

import jdanimal.demo.service.AccessoryService;
import jdanimal.demo.service.AnimalService;
import jdanimal.demo.service.UserService;
import jdanimal.demo.service.views.AccessoryViewModel;
import jdanimal.demo.service.views.AnimalViewModel;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
@AllArgsConstructor
public class HomeController {

    private final AnimalService animalService;
    private final AccessoryService accessoryService;
    private final UserService userService;

    @GetMapping("/users/login")
    public String logIn(){
        return "index";
    }

    @GetMapping("/access-denied")
    public String accessDeniedPage(){
        return "access-denied";
    }

    @GetMapping("/")
    public String index(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            return "index";
        }
        return "redirect:/user/home";
    }


    @GetMapping("/logout")
    public String logOut() {
        return "index";

    }

    @GetMapping("/user/home")
    public String getHome(Model model){      
        List<AnimalViewModel> allAnimals = this.animalService.getAllAnimals();
        List<AccessoryViewModel> allAccessories = this.accessoryService.getAllAccessories();
        model.addAttribute("accessories",allAccessories);
        model.addAttribute("animals",allAnimals);
        return "home";
    }
}

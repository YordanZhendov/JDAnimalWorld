package jdanimal.demo.web.controllers;

import jdanimal.demo.service.AnimalService;
import jdanimal.demo.service.views.AnimalViewModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
@AllArgsConstructor
public class HomeController {

    private final AnimalService animalService;

    @GetMapping("/users/login")
    public String logIn(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
           
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

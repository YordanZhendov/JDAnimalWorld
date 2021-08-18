package jdanimal.demo.web.controllers;

import jdanimal.demo.service.AnimalService;
import jdanimal.demo.service.views.AnimalViewModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.WebUtils;
import org.springframework.security.core.Authentication;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import java.util.List;
import java.net.*;

@Controller
@AllArgsConstructor
public class HomeController {

    private final AnimalService animalService;

    @GetMapping("/users/login")
    public String logIn(){
        if (isAuthenticated()) {
        return "redirect:/user/home";
        }
        return "index";
    }
    
    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
          isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }
    
     @GetMapping("/")
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();      
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

package jdanimal.demo.web.controllers;

import jdanimal.demo.service.AnimalService;
import jdanimal.demo.service.UserService;
import jdanimal.demo.service.models.UserAnimalUploadModel;
import jdanimal.demo.service.views.UserProfileViewModel;
import jdanimal.demo.service.views.AnimalViewModel;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class ProfileController {

    private final UserService userService;
    private final AnimalService animalService;


    @GetMapping("/profile")
    public String getUserProfile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            UserProfileViewModel userProfileInfo = userService.findByUsername(currentUserName);
            List<AnimalViewModel> allAnimalsByUser = userService.getAllAnimalsByUser(userProfileInfo.getUsername());

            model.addAttribute("userProfileInfo",userProfileInfo);
            model.addAttribute("userAnimal",allAnimalsByUser);
            return "profile";
        }
        return null;
    }


}

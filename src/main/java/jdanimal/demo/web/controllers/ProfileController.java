package jdanimal.demo.web.controllers;

import jdanimal.demo.service.UserService;
import jdanimal.demo.web.models.UserProfileModel;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class ProfileController {

    private final UserService userService;


    @GetMapping("/profile")
    public String getUserProfile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            UserProfileModel userProfileInfo = userService.findByUsername(currentUserName);

            model.addAttribute("userProfileInfo",userProfileInfo);
            return "profile";
        }
        return null;

    }

    //TOdO extract profile info
}

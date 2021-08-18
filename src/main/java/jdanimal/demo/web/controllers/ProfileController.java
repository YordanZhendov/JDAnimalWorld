package jdanimal.demo.web.controllers;

import jdanimal.demo.service.AnimalService;
import jdanimal.demo.service.UserService;
import jdanimal.demo.service.models.UserAnimalUploadModel;
import jdanimal.demo.service.models.UserRegistrationModel;
import jdanimal.demo.service.models.UserUpdateProfileModel;
import jdanimal.demo.service.views.UserProfileViewModel;
import jdanimal.demo.service.views.AnimalViewModel;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class ProfileController {

    private final UserService userService;

    @GetMapping("/profile")
    public String getUserProfile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            UserProfileViewModel userProfileInfo = this.userService.findByUsername(currentUserName);
            List<AnimalViewModel> allAnimalsByUser = this.userService.getAllAnimalsByUser(userProfileInfo.getUsername());

            if(!model.containsAttribute("userUpdateProfileModel")){
                model.addAttribute("userUpdateProfileModel",new UserUpdateProfileModel());
            }

            model.addAttribute("userProfileInfo",userProfileInfo);
            model.addAttribute("userAnimal",allAnimalsByUser);
            return "profile";
        }

        return "index";

    }

    @PostMapping("/update-profile")
    public String updateProfile(@Valid UserUpdateProfileModel userUpdateProfileModel,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("userUpdateProfileModel",userUpdateProfileModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userUpdateProfileModel",bindingResult);

            return "redirect:/user/profile";

        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userUpdateProfileModel.setUsername(authentication.getName());
        this.userService.updateProfile(userUpdateProfileModel);
        return "redirect:/user/profile";
    }


}

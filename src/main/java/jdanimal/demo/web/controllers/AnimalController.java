package jdanimal.demo.web.controllers;

import jdanimal.demo.service.AnimalService;
import jdanimal.demo.service.UserService;
import jdanimal.demo.service.models.UserAnimalUploadModel;
import jdanimal.demo.service.views.UserProfileViewModel;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class AnimalController {

    private final UserService userService;
    private final AnimalService animalService;

    //animal form page
    @GetMapping("/animal")
    public String addAnimal(Model model){
        if(!model.containsAttribute("userAnimalUploadModel")){
            model.addAttribute("userAnimalUploadModel",new UserAnimalUploadModel());
        }
        return "animal";
    }

    //animal upload
    @PostMapping("/animal/upload")
    public String uploadAnimal(@Valid UserAnimalUploadModel userAnimalUploadModel,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute(
                    "userAnimalUploadModel",userAnimalUploadModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userAnimalUploadModel",bindingResult);
            return "redirect:/user/animal";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();

            UserProfileViewModel userProfileInfo = this.userService.findByUsername(currentUserName);
            this.animalService.uploadAnimal(userAnimalUploadModel,userProfileInfo);
            return "redirect:/user/home";
        }
        return null;
    }

}

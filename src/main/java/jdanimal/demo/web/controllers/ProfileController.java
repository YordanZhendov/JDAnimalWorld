package jdanimal.demo.web.controllers;

import jdanimal.demo.data.DTO.UserAnimalUploadDTO;
import jdanimal.demo.service.AnimalService;
import jdanimal.demo.service.UserService;
import jdanimal.demo.service.models.UserAnimalUploadModel;
import jdanimal.demo.service.models.UserProfileModel;
import jdanimal.demo.service.views.AnimalViewModel;
import jdanimal.demo.service.views.AnimalsViewDeleteModel;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    private final AnimalService animalService;


    @GetMapping("/profile")
    public String getUserProfile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            UserProfileModel userProfileInfo = userService.findByUsername(currentUserName);
            List<AnimalViewModel> allAnimalsByUser = userService.getAllAnimalsByUser(userProfileInfo.getUsername());

            model.addAttribute("userProfileInfo",userProfileInfo);
            model.addAttribute("userAnimal",allAnimalsByUser);
            return "profile";
        }
        return null;
    }

    @GetMapping("/animaladd")
    public String addAnimal(Model model){
        if(!model.containsAttribute("userAnimalUploadModel")){
            model.addAttribute("userAnimalUploadModel",new UserAnimalUploadModel());
        }
        return "animaladd";
    }

    @PostMapping("/animal/upload")
    public String uploadAnimal(@Valid UserAnimalUploadModel userAnimalUploadModel,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute(
                    "userAnimalUploadModel",userAnimalUploadModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userAnimalUploadModel",bindingResult);
            return "redirect:/user/animaladd";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            UserProfileModel userProfileInfo = userService.findByUsername(currentUserName);

          this.userService.uploadAnimal(userAnimalUploadModel,userProfileInfo);
            return "redirect:/user/profile";
        }
        return null;
    }

    @PostMapping("/animal/delete")
    public String deleteAnimal(AnimalsViewDeleteModel animalsViewDeleteModel){

        animalService.deleteByNameOfAnimal(animalsViewDeleteModel.getNameOfAnimal());

        return "redirect:/user/profile";
    }

}

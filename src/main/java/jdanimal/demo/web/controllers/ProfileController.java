package jdanimal.demo.web.controllers;

import jdanimal.demo.data.Accessory;
import jdanimal.demo.data.Animal;
import jdanimal.demo.service.AccessoryService;
import jdanimal.demo.service.AnimalService;
import jdanimal.demo.service.StoreService;
import jdanimal.demo.service.UserService;
import jdanimal.demo.service.impl.StorageServiceImpl;
import jdanimal.demo.service.models.UserUpdateProfileModel;
import jdanimal.demo.service.views.AccessoryViewModel;
import jdanimal.demo.service.views.StoreViewModel;
import jdanimal.demo.service.views.UserProfileViewModel;
import jdanimal.demo.service.views.AnimalViewModel;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class ProfileController {

    private final UserService userService;
    private final StorageServiceImpl storageService;
    private final StoreService storeService;

    //profile page
    @GetMapping("/profile")
    public String getUserProfile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            UserProfileViewModel userProfileInfo = this.userService.findByUsername(currentUserName);
            String username = userProfileInfo.getUsername();

            List<AnimalViewModel> allAnimalsByUser = this.userService.getAllAnimalsByUser(username);
            List<AccessoryViewModel> allAccessoriesByUser = this.userService.getAllAccessoriesByUser(username);
            List<StoreViewModel> allStoresByUser = this.storeService.getAllStoresByUser(username);
            Set<Animal> likedAnimals = userProfileInfo.getLikedAnimals();
            Set<Accessory> likedAccessories = userProfileInfo.getLikedAccessories();

            if(!model.containsAttribute("userUpdateProfileModel")){
                model.addAttribute("userUpdateProfileModel",new UserUpdateProfileModel());
            }

            model.addAttribute("userProfileInfo",userProfileInfo);
            model.addAttribute("userAnimal",allAnimalsByUser);
            model.addAttribute("allStoresByUser",allStoresByUser);
            model.addAttribute("allAccessoriesByUser",allAccessoriesByUser);
            model.addAttribute("likedAnimals",likedAnimals);
            model.addAttribute("likedAccessories",likedAccessories);

            return "profile";
        }

        return "index";

    }

    //upload profile picture
    @PostMapping("/foto/upload")
    public String uploadPicture(@RequestParam(value = "file") MultipartFile file){
        boolean correctPicture = storageService.checkFile(file.getOriginalFilename(),file.getSize());
        if(!correctPicture){
            return "redirect:/user/profile";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        UserProfileViewModel userProfileInfo = this.userService.findByUsername(currentUserName);
        storageService.upload(file,userProfileInfo);
        return "redirect:/user/profile";
    };

    //update profile details
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

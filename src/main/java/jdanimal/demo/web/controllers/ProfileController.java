package jdanimal.demo.web.controllers;

import jdanimal.demo.data.Accessory;
import jdanimal.demo.data.Animal;
import jdanimal.demo.service.StoreService;
import jdanimal.demo.service.UserService;
import jdanimal.demo.service.impl.StorageServiceImpl;
import jdanimal.demo.web.binding.UserUpdateProfileBinding;
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
        String currentUserName = authentication.getName();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            UserProfileViewModel userProfileInfo = this.userService.findByUsername(currentUserName);

            String username = userProfileInfo.getUsername();

            List<AnimalViewModel> allAnimalsByUser = this.userService.getAllAnimalsByUserName(username);
            List<AccessoryViewModel> allAccessoriesByUser = this.userService.getAllAccessoriesByUserName(username);
            List<StoreViewModel> allStoresByUser = this.storeService.getAllStoresByUser(username);
            Set<Animal> likedAnimals = userProfileInfo.getLikedAnimals();
            Set<Accessory> likedAccessories = userProfileInfo.getLikedAccessories();

            model.addAttribute("userProfileInfo",userProfileInfo);
            model.addAttribute("userAnimal",allAnimalsByUser);
            model.addAttribute("allStoresByUser",allStoresByUser);
            model.addAttribute("allAccessoriesByUser",allAccessoriesByUser);
            model.addAttribute("likedAnimals",likedAnimals);
            model.addAttribute("likedAccessories",likedAccessories);

            if(!model.containsAttribute("isFound")){
                model.addAttribute("isFound",true);
            }

            return "profile";
        }

        return "index";

    }

    //upload profile picture
    @PostMapping("/foto/upload")
    public String uploadPicture(@RequestParam(value = "file") MultipartFile file){
        boolean correctPicture = this.storageService.checkFile(file.getOriginalFilename(),file.getSize());
        if(!correctPicture){
            return "redirect:/user/profile";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        UserProfileViewModel userProfileInfo = this.userService.findByUsername(currentUserName);
        this.storageService.upload(file,userProfileInfo);
        return "redirect:/user/profile";
    };

    //update profile details
    @PostMapping("/update-profile")
    public String updateProfile(@Valid UserUpdateProfileBinding userUpdateProfileBinding,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("userUpdateProfileBinding", userUpdateProfileBinding);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userUpdateProfileBinding",bindingResult);

            return "redirect:/user/profile";

        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        userUpdateProfileBinding.setUsername(currentUserName);
        boolean detailsFound = this.userService.updateProfile(userUpdateProfileBinding);
        if(!detailsFound){
            redirectAttributes.addFlashAttribute("isFound",false);
            return "redirect:/user/profile";
        }
        return "redirect:/user/profile";
    }

    @ModelAttribute
    public UserUpdateProfileBinding userUpdateProfileBinding(){
        return new UserUpdateProfileBinding();
    }

}

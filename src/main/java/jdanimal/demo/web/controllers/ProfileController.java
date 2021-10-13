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

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class ProfileController {

    private final UserService userService;
    private final StorageServiceImpl storageService;
    private final AnimalService animalService;
    private final AccessoryService accessoryService;
    private final StoreService storeService;

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

    @PostMapping("/animal/uploadPhoto/{id}")
    public String uploadAnimalPicture(@PathVariable(value = "id") String id,@RequestParam(value = "fileAnimal") MultipartFile fileAnimal){
        boolean correctPicture = storageService.checkFile(fileAnimal.getOriginalFilename(),fileAnimal.getSize());
        if(!correctPicture){
            return "redirect:/user/profile";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        UserProfileViewModel userProfileInfo = this.userService.findByUsername(currentUserName);

        storageService.uploadAnimalPicture(fileAnimal,userProfileInfo,id);


        return "redirect:/user/profile";
    }

    @GetMapping("/animal/delete/{id}")
    public String deleteAnimal(@PathVariable(value = "id") String id){
        animalService.removeAnimal(id);
        return "redirect:/user/profile";
    }

    @GetMapping("/animal/like/{id}")
    public String likeAnimal(@PathVariable(value = "id") String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        animalService.addLikedAnimalTotheCurrentUser(id,currentUserName);

        return "redirect:/user/home";
    };

    @GetMapping("/animal/dislike/{id}")
    public String dislikeAnimal(@PathVariable(value = "id") String  id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        animalService.disLikedAnimalTotheCurrentUser(id,currentUserName);

        return "redirect:/user/profile";
    }

    @PostMapping("/accessory/uploadPhoto/{id}")
    public String uploadAccessoryPicture(@PathVariable(value = "id") String id,@RequestParam(value = "fileAccessory") MultipartFile fileAccessory){
        boolean correctPicture = storageService.checkFile(fileAccessory.getOriginalFilename(),fileAccessory.getSize());
        if(!correctPicture){
            return "redirect:/user/profile";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        UserProfileViewModel userProfileInfo = this.userService.findByUsername(currentUserName);

        storageService.uploadAccessoryPicture(fileAccessory,userProfileInfo,id);


        return "redirect:/user/profile";
    }

    @GetMapping("/accessory/delete/{id}")
    public String deleteAccessory(@PathVariable(value = "id") String id){
        accessoryService.removeAccessory(id);
        return "redirect:/user/profile";
    }

    @GetMapping("/accessory/like/{id}")
    public String likeAccessory(@PathVariable(value = "id") String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        accessoryService.addLikedAccessoryTotheCurrentUser(id,currentUserName);

        return "redirect:/user/home";
    };

    @GetMapping("/accessory/dislike/{id}")
    public String dislikeAccessory(@PathVariable(value = "id") String  id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        accessoryService.disLikedAccessoryTotheCurrentUser(id,currentUserName);

        return "redirect:/user/profile";
    }

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

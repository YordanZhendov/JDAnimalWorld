package jdanimal.demo.web.controllers;

import jdanimal.demo.service.AccessoryService;
import jdanimal.demo.service.AnimalService;
import jdanimal.demo.service.UserService;
import jdanimal.demo.service.impl.StorageServiceImpl;
import jdanimal.demo.service.views.UserProfileViewModel;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class AniAccStProfileController {

    private final UserService userService;
    private final StorageServiceImpl storageService;
    private final AnimalService animalService;
    private final AccessoryService accessoryService;

    //Animal
    //upload animal picture
    @PostMapping("/animal/uploadPhoto/{id}")
    public String uploadAnimalPicture(@PathVariable(value = "id") String id, @RequestParam(value = "fileAnimal") MultipartFile fileAnimal){
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

    //animal delete
    @GetMapping("/animal/delete/{id}")
    public String deleteAnimal(@PathVariable(value = "id") String id){
        animalService.removeAnimal(id);
        return "redirect:/user/profile";
    }

    //animal like
    @GetMapping("/animal/like/{id}")
    public String likeAnimal(@PathVariable(value = "id") String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        animalService.addLikedAnimalToTheCurrentUser(id,currentUserName);

        return "redirect:/user/home";
    };

    //animal dislike
    @GetMapping("/animal/dislike/{id}")
    public String dislikeAnimal(@PathVariable(value = "id") String  id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        animalService.disLikedAnimalToTheCurrentUser(id,currentUserName);

        return "redirect:/user/profile";
    }

//    ____________________________________________________________

    //Accessory
    //accessory upload picture
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

    //accessory delete
    @GetMapping("/accessory/delete/{id}")
    public String deleteAccessory(@PathVariable(value = "id") String id){
        accessoryService.removeAccessoryById(id);
        return "redirect:/user/profile";
    }

    //accessory like
    @GetMapping("/accessory/like/{id}")
    public String likeAccessory(@PathVariable(value = "id") String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        accessoryService.addLikedAccessoryToTheCurrentUser(id,currentUserName);

        return "redirect:/user/home";
    };

    //accessory like
    @GetMapping("/accessory/dislike/{id}")
    public String dislikeAccessory(@PathVariable(value = "id") String  id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        accessoryService.disLikedAccessoryToTheCurrentUser(id,currentUserName);

        return "redirect:/user/profile";
    }
}

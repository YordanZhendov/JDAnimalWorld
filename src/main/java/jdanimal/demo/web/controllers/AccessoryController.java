package jdanimal.demo.web.controllers;

import jdanimal.demo.service.AccessoryService;
import jdanimal.demo.service.UserService;
import jdanimal.demo.web.binding.UserAccessoryUploadBinding;
import jdanimal.demo.service.views.UserProfileViewModel;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class AccessoryController {

    private final AccessoryService accessoryService;
    private final UserService userService;

    //accessory page
    @GetMapping("/user/accessory")
    public String accessoryPage(Model model) {

        if(!model.containsAttribute("userAccessoryUploadBinding")){
            model.addAttribute("userAccessoryUploadBinding",new UserAccessoryUploadBinding());
        }
        return "accessory";

    }

    //accessory upload
    @PostMapping("/user/accessory/upload")
    public String accessoryUpload(@Valid UserAccessoryUploadBinding userAccessoryUploadBinding,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute(
                    "userAccessoryUploadBinding", userAccessoryUploadBinding);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userAccessoryUploadBinding",bindingResult);
            return "redirect:/user/accessory";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        UserProfileViewModel byUsername = this.userService.findByUsername(currentUserName);
        this.accessoryService.upload(userAccessoryUploadBinding,byUsername);

        return "redirect:/user/home";

    }
}
package jdanimal.demo.web.controllers;

import jdanimal.demo.service.models.UserAnimalUploadModel;
import jdanimal.demo.service.models.UserRegistrationModel;
import jdanimal.demo.service.UserService;
import jdanimal.demo.data.DTO.UserRegisterDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class RegisterController {

    private final UserService userService;

    //Registration page
    @GetMapping("/users/register")
        public String register(Model model){
            if(!model.containsAttribute("isFound")){
                model.addAttribute("isFound",true);
            }
            return "register";

    }

    //Registration form, details checked with JS and check for existing user with validation
    @PostMapping("/users/register")
    public String registerUser(@Valid UserRegistrationModel userRegistrationModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){


        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("userRegistrationModel",userRegistrationModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationModel",bindingResult);

            return "redirect:/users/register";
        }

        boolean userNotFound = this.userService.register(userRegistrationModel);

        if(!userNotFound){
            redirectAttributes.addFlashAttribute("isFound",false);
            return "redirect:/users/register";
        }

        return "redirect:/users/login";
    }

    @ModelAttribute
    public UserRegistrationModel userRegistrationModel(){
        return new UserRegistrationModel();
    }
}













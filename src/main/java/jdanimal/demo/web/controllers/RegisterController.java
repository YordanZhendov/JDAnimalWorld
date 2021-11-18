package jdanimal.demo.web.controllers;

import jdanimal.demo.web.binding.UserRegistrationBinding;
import jdanimal.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
            if(!model.containsAttribute("correctInfoDetails")){
                model.addAttribute("correctInfoDetails",true);
            }
            return "register";

    }

    //Registration form, details checked with JS and check for existing user with validation
    @PostMapping("/users/register")
    public String registerUser(@Valid UserRegistrationBinding userRegistrationBinding,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){


        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("userRegistrationBinding", userRegistrationBinding);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationBinding",bindingResult);
            redirectAttributes.addFlashAttribute("correctInfoDetails",false);

            return "redirect:/users/register";
        }

        boolean userNotFound = this.userService.register(userRegistrationBinding);

        if(!userNotFound){
            redirectAttributes.addFlashAttribute("isFound",false);
            return "redirect:/users/register";
        }

        redirectAttributes.addFlashAttribute("registeredSuccessfully",false);
        return "redirect:/users/login";
    }

    @ModelAttribute
    public UserRegistrationBinding userRegistrationModel(){
        return new UserRegistrationBinding();
    }
}













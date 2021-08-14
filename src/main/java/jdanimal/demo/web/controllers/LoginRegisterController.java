package jdanimal.demo.web.controllers;

import jdanimal.demo.service.models.UserRegistrationModel;
import jdanimal.demo.service.UserService;
import jdanimal.demo.data.DTO.UserRegisterDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class LoginRegisterController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/login")
    public String getLogin(){
        return "index";

    }
    @GetMapping("/register")
        public String register(Model model){
            if(!model.containsAttribute("userRegistrationModel")){
                model.addAttribute("userRegistrationModel",new UserRegistrationModel());
            }
            return "register";

    }

    @PostMapping("/register")
    public String registerUser(@Valid UserRegistrationModel userRegistrationModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("userRegistrationModel",userRegistrationModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationModel",bindingResult);
            return "redirect:/users/register";
        }

        UserRegisterDTO mappedUser = modelMapper.map(userRegistrationModel, UserRegisterDTO.class);
        this.userService.register(mappedUser);
        return "redirect:/users/login";
    }
}













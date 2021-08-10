package jdanimal.demo.web.controllers;

import jdanimal.demo.data.DTO.UserRegistrationDTO;
import jdanimal.demo.service.UserService;
import jdanimal.demo.service.models.UserRegisterModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @PostMapping("/register")
    public String register(UserRegisterModel userRegisterModel){
        UserRegistrationDTO userRegister = modelMapper.map(userRegisterModel, UserRegistrationDTO.class);
        this.userService.register(userRegister);
        return "redirect:/login";
    }

}

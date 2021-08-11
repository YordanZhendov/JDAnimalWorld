package jdanimal.demo.web.controllers;

import jdanimal.demo.service.models.UserRegistrationModel;
import jdanimal.demo.service.UserService;
import jdanimal.demo.data.DTO.UserRegisterDTO;
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
    public String register(UserRegisterDTO userRegisterDTO){
        UserRegistrationModel userRegister = modelMapper.map(userRegisterDTO, UserRegistrationModel.class);
        this.userService.register(userRegister);
        return "redirect:/login";
    }

}

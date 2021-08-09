package jdanimal.demo.web.controllers;

import jdanimal.demo.data.DTO.UserLoginDTO;
import jdanimal.demo.data.DTO.UserRegistrationDTO;
import jdanimal.demo.data.User;
import jdanimal.demo.repository.RoleRepository;
import jdanimal.demo.service.RoleService;
import jdanimal.demo.service.UserService;
import jdanimal.demo.web.models.UserLoginModel;
import jdanimal.demo.web.models.UserRegisterModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class LoginController {

    private final UserService userService;
    private final ModelMapper modelMapper;



    @GetMapping("/")
    public String login(){
        return "index";

    }

    @GetMapping("/home")
    public String getHome(){
        return "home";

    }
    @GetMapping("/users/login")
    public String getLogin(){
        return "index";

    }

    @PostMapping("/users/register")
    public String register(UserRegisterModel userRegisterModel){
        UserRegistrationDTO userRegister = modelMapper.map(userRegisterModel, UserRegistrationDTO.class);
        this.userService.register(userRegister);
        return "redirect:/";
    }

    @GetMapping("/animaladd")
    public String addAnimal(){
        return "animaladd";
    }

    @GetMapping("/logout")
    public String logOut(){
        return "redirect:/";
    }
}

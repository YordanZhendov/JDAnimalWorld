package jdanimal.demo.web.controllers;

import jdanimal.demo.service.UserService;
import jdanimal.demo.service.views.UserProfileViewModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserControlController {

    private final UserService userService;

    //admin control page
    @GetMapping("/users-control")
    public String userControlPage(Model model){

        List<UserProfileViewModel> users=this.userService.getAllUsersForUserControl();

        model.addAttribute("users",users);

        return "users-control";
    }

    //delete user
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") String id){
        this.userService.removeUser(id);
        return "redirect:/user/users-control";
    };

    @GetMapping("/deactive/temporary/{id}")
    public String suspendAccount(@PathVariable(name = "id") String id){
        this.userService.suspendUser(id);
        return "redirect:/user/users-control";
    }

    @GetMapping("/activate/{id}")
    public String activateUser(@PathVariable(name = "id") String id){
        this.userService.activateUser(id);
        return "redirect:/user/users-control";
    }

}

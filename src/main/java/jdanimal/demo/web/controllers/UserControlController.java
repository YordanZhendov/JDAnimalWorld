package jdanimal.demo.web.controllers;

import jdanimal.demo.service.AccessoryService;
import jdanimal.demo.service.AnimalService;
import jdanimal.demo.service.views.AccessoryViewModel;
import jdanimal.demo.service.views.AnimalViewModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;

@Controller
@AllArgsConstructor
public class UserControlController {


    @GetMapping("/user/users-control")
    public String userControlPage(){
        return "users-control";
    }

}
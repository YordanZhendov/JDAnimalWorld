package jdanimal.demo.web.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class EncyclopediaController {

    @GetMapping("/encyclopedia/add")
    public String add(){
        return "addencyclopedia";
    }
}

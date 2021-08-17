package jdanimal.demo.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessoryController {

    @GetMapping("/user/accessory")
    public String accessoryUpload() {
        return "accessory";

    }
}
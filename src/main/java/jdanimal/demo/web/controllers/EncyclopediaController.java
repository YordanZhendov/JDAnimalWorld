package jdanimal.demo.web.controllers;

import jdanimal.demo.web.binding.EncyclopediaAnimalBinding;
import jdanimal.demo.service.EncyclopediaService;
import jdanimal.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class EncyclopediaController {

    private final EncyclopediaService encyclopediaService;
    private final UserService userService;

    //encyclopedia form page
    @GetMapping("/encyclopedia/add")
    public String add(){
        return "addencyclopedia";
    }

    //encyclipedia page
    @GetMapping("/user/animalencyclopedia")
    public String animalEncyclopedia(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        int size = this.userService.findByUsername(authentication.getName()).getAuthorities().size();

        if(size == 1){
            model.addAttribute("hide",true);
        }

        model.addAttribute("animals",this.encyclopediaService.findAllAnimalsInEncyclopedia());
        return "animalencyclopedia";
    }

    //encyclopedia upload
    @PostMapping("/add/animal/encyclopedia")
    public String addAnimaltoEncyclopedia(@Valid EncyclopediaAnimalBinding encyclopediaAnimalBinding,
                                          BindingResult bindingResult,
                                          RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute(
                    "encyclopediaAnimalBinding",encyclopediaAnimalBinding);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.encyclopediaAnimalBinding",bindingResult);
            return "redirect:/encyclopedia/add";
        }

        this.encyclopediaService.saveInDB(encyclopediaAnimalBinding);
        return "redirect:/user/animalencyclopedia";
    }

    //encyclopedia delete
    @GetMapping("/encyclopedia/animal/delete/{id}")
    public String deletAnimaltoEncyclopediaeAnimal(@PathVariable(name = "id") String id){
        this.encyclopediaService.removeAnimalFromEncyclopedia(id);
        return "redirect:/user/animalencyclopedia";
    }

    //encyclopedia delete
    @GetMapping("/animal/filter/{name}")
    public String filterbyAnimal(@PathVariable(name = "name") String type,
                                 Model model){
        model.addAttribute("animals",this.encyclopediaService.animalsFilterbyName(type));
        return "animalencyclopedia";
    }

    @ModelAttribute
    public EncyclopediaAnimalBinding encyclopediaAnimalBinding(){
        return new EncyclopediaAnimalBinding();
    }
}

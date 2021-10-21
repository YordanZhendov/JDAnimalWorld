package jdanimal.demo.web.controllers;

import com.sun.xml.bind.v2.TODO;
import jdanimal.demo.service.StoreService;
import jdanimal.demo.service.UserService;
import jdanimal.demo.service.models.UserAnimalUploadModel;
import jdanimal.demo.service.models.UserStoreUploadModel;
import jdanimal.demo.service.views.StoreViewModel;
import jdanimal.demo.service.views.UserProfileViewModel;
import lombok.AllArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class StoreController {

    private final StoreService storeService;

    //store form page
    @GetMapping("/addanimaltore")
    public String addStore(Model model){
        if(!model.containsAttribute("userStoreUploadModel")){
            model.addAttribute("userStoreUploadModel",new UserStoreUploadModel());
        }
        return "addstore";
    }

    //stores page
    @GetMapping("/animalstores")
    public String store(Model model){
        model.addAttribute("storeViewModel",this.storeService.getAllStores());
        return "store";
    }

    //store upload
    @PostMapping("/store/upload")
    public String uploadStore(@Valid UserStoreUploadModel userStoreUploadModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute(
                    "userStoreUploadModel",userStoreUploadModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userStoreUploadModel",bindingResult);
            return "redirect:/user/addanimaltore";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            this.storeService.uploadStore(userStoreUploadModel,currentUserName);
            return "redirect:/user/animalstores";
        }
        return null;

    };

    //store delete
    @GetMapping("/store/delete/{id}")
    public String deleteStore(@PathVariable(name = "id") String id){
        this.storeService.removeStore(id);
        return "redirect:/user/profile";
    };

}

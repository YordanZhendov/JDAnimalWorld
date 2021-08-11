package jdanimal.demo.service;


import jdanimal.demo.service.models.UserAnimalUploadModel;
import jdanimal.demo.data.DTO.UserLoginDTO;
import jdanimal.demo.service.models.UserRegistrationModel;
import jdanimal.demo.data.User;
import jdanimal.demo.service.models.UserProfileModel;
import jdanimal.demo.service.views.AnimalViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    void register(UserRegistrationModel userRegistrationModel);
    User validUser(UserLoginDTO userLoginDTO);
    UserProfileModel findByUsername(String currentUserName);
    void uploadAnimal(UserAnimalUploadModel userAnimalUploadBindingModel, UserProfileModel userProfileInfo);
    List<AnimalViewModel> getAllAnimalsByUser(String id);
}

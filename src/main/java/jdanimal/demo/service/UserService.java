package jdanimal.demo.service;


import jdanimal.demo.service.models.UserAnimalUploadModel;
import jdanimal.demo.data.DTO.UserLoginDTO;
import jdanimal.demo.data.DTO.UserRegistrationDTO;
import jdanimal.demo.data.User;
import jdanimal.demo.service.models.UserProfileModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void register(UserRegistrationDTO userRegistrationDTO);
    User validUser(UserLoginDTO userLoginDTO);
    UserProfileModel findByUsername(String currentUserName);
    void uploadAnimal(UserAnimalUploadModel userAnimalUploadBindingModel, UserProfileModel userProfileInfo);
}

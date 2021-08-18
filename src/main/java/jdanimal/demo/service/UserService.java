package jdanimal.demo.service;


import jdanimal.demo.data.DTO.UserRegisterDTO;
import jdanimal.demo.service.models.UserAnimalUploadModel;
import jdanimal.demo.data.DTO.UserLoginDTO;
import jdanimal.demo.data.User;
import jdanimal.demo.service.models.UserUpdateProfileModel;
import jdanimal.demo.service.views.UserProfileViewModel;
import jdanimal.demo.service.views.AnimalViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    void register(UserRegisterDTO userRegisterDTO);
    User validUser(UserLoginDTO userLoginDTO);
    UserProfileViewModel findByUsername(String currentUserName);
    List<AnimalViewModel> getAllAnimalsByUser(String id);
    void updateProfile(UserUpdateProfileModel userUpdateProfileModel);
}

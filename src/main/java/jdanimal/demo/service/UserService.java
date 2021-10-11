package jdanimal.demo.service;


import jdanimal.demo.data.Accessory;
import jdanimal.demo.data.Animal;
import jdanimal.demo.data.DTO.UserRegisterDTO;
import jdanimal.demo.service.models.UserAnimalUploadModel;
import jdanimal.demo.data.DTO.UserLoginDTO;
import jdanimal.demo.data.User;
import jdanimal.demo.service.models.UserUpdateProfileModel;
import jdanimal.demo.service.views.AccessoryViewModel;
import jdanimal.demo.service.views.UserProfileViewModel;
import jdanimal.demo.service.views.AnimalViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    void register(UserRegisterDTO userRegisterDTO);
    User validUser(UserLoginDTO userLoginDTO);
    UserProfileViewModel findByUsername(String currentUserName);
    List<AnimalViewModel> getAllAnimalsByUser(String username);
    void updateProfile(UserUpdateProfileModel userUpdateProfileModel);
    List<User> getAllUsersInDB();
    void saveUrl(String username,String fileName);
    void removeAnimalFromUsers(Animal animalById);
    List<AccessoryViewModel> getAllAccessoriesByUser(String username);
    void removeAccessoryFromUsers(Accessory accessoryById);
}

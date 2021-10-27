package jdanimal.demo.service;


import jdanimal.demo.data.Accessory;
import jdanimal.demo.data.Animal;
import jdanimal.demo.web.binding.UserLoginBinding;
import jdanimal.demo.data.User;
import jdanimal.demo.web.binding.UserRegistrationBinding;
import jdanimal.demo.web.binding.UserUpdateProfileBinding;
import jdanimal.demo.service.views.AccessoryViewModel;
import jdanimal.demo.service.views.UserProfileViewModel;
import jdanimal.demo.service.views.AnimalViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    boolean register(UserRegistrationBinding userRegistrationBinding);
    User validUser(UserLoginBinding userLoginBinding);
    UserProfileViewModel findByUsername(String currentUserName);
    List<AnimalViewModel> getAllAnimalsByUser(String username);
    void updateProfile(UserUpdateProfileBinding userUpdateProfileBinding);
    List<User> getAllUsersInDB();
    void saveUrl(String username,String fileName);
    void removeAnimalFromUsers(Animal animalById);
    List<AccessoryViewModel> getAllAccessoriesByUser(String username);
    void removeAccessoryFromUsers(Accessory accessoryById);
    void removeUser(String id);
    List<UserProfileViewModel> getAllUsersForUserControl();
    void sendEmail();
    void suspendUser(String id);
    void activateUser(String id);
    String checkUserStatus(String currentUserName);
    void updateCash();

}

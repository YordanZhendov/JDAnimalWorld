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

    boolean suspendUser(String id);
    boolean activateUser(String id);
    boolean updateCash();

    UserProfileViewModel findByUsername(String currentUserName);
    List<AnimalViewModel> getAllAnimalsByUserName(String username);
    void updateProfile(UserUpdateProfileBinding userUpdateProfileBinding);

    void saveUrl(String username,String fileName);
    void removeAnimalFromUsers(Animal animalById);
    List<AccessoryViewModel> getAllAccessoriesByUser(String username);
    void removeAccessoryFromUsers(Accessory accessoryById);
    void removeUser(String id);
    List<UserProfileViewModel> getAllUsersForUserControl();

    boolean sendEmail();
}

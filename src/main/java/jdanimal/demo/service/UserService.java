package jdanimal.demo.service;


import jdanimal.demo.data.Accessory;
import jdanimal.demo.data.Animal;
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

    boolean saveUrl(String username,String fileName);
    boolean removeLikedAnimalFromUsers(Animal animalById);
    List<AccessoryViewModel> getAllAccessoriesByUserName(String username);
    boolean removeLikedAccessoryFromUsers(Accessory accessoryById);
    boolean removeUser(String id);
    boolean sendEmail();

    List<UserProfileViewModel> getAllUsersForUserControl();

}

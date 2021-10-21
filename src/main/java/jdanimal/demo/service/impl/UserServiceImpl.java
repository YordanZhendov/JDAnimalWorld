package jdanimal.demo.service.impl;

import jdanimal.demo.data.*;
import jdanimal.demo.data.DTO.UserRegisterDTO;
import jdanimal.demo.data.DTO.UserLoginDTO;
import jdanimal.demo.data.DTO.UserProfileDTO;
import jdanimal.demo.repository.AccessoryRepository;
import jdanimal.demo.repository.AnimalRepository;
import jdanimal.demo.repository.StoreRepository;
import jdanimal.demo.repository.UserRepository;
import jdanimal.demo.service.RoleService;
import jdanimal.demo.service.UserService;
import jdanimal.demo.service.UserValidationSerivce;
import jdanimal.demo.service.models.UserRegistrationModel;
import jdanimal.demo.service.models.UserUpdateProfileModel;
import jdanimal.demo.service.views.AccessoryViewModel;
import jdanimal.demo.service.views.AnimalViewModel;
import jdanimal.demo.util.ValidationUtil;
import jdanimal.demo.service.views.UserProfileViewModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AnimalRepository animalRepository;
    private final AccessoryRepository accessoryRepository;
    private final StoreRepository storeRepository;
    private final UserValidationSerivce userValidationSerivce;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;
    private final RoleService roleService;
    private final ValidationUtil validationUtil;


    @Override
    public boolean register(UserRegistrationModel userRegistrationModel) {
        UserRegisterDTO userRegisterDTO = this.modelMapper.map(userRegistrationModel, UserRegisterDTO.class);

        if(!validationUtil.isValid(userRegisterDTO)){
            return false;
        }

        User user=this.modelMapper.map(userRegisterDTO,User.class);

        if(!userValidationSerivce.checkUser(user)){
            return false;
        }

        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        user.setConfirmPassword(this.bCryptPasswordEncoder.encode(user.getConfirmPassword()));

        if(userRepository.count() == 0){
            roleService.seedRoles();
            user.setAuthorities(this.roleService.findAllRoles()
                    .stream().map(r->this.modelMapper
                            .map(r,Role.class))
                    .collect(Collectors.toSet()));
        }else {

            user.setAuthorities(new LinkedHashSet<>());
            user.getAuthorities().add(this.modelMapper.map(this.roleService.findByAuthority("GUEST"),Role.class));
        }
        this.userRepository.save(user);
        return true;

    }

    @Override
    public User validUser(UserLoginDTO userLoginDTO) {
        userLoginDTO.setPassword(this.bCryptPasswordEncoder.encode(userLoginDTO.getPassword()));
        User userLogin = this.modelMapper.map(userLoginDTO, User.class);
        return this.userRepository.findByUsernameAndPassword(userLogin.getUsername(), userLogin.getPassword()).orElse(null);
    }

    @Override
    public UserProfileViewModel findByUsername(String currentUserName) {
        User byUsername = this.userRepository.findByUsername(currentUserName);
        UserProfileDTO userProfileDTO = this.modelMapper.map(byUsername, UserProfileDTO.class);
        return this.modelMapper.map(userProfileDTO, UserProfileViewModel.class);
    }



    @Override
    public List<AnimalViewModel> getAllAnimalsByUser(String username) {
        return this.animalRepository.getAnimalByUser(username)
                .stream()
                .map(animal -> this.modelMapper.map(animal,AnimalViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AccessoryViewModel> getAllAccessoriesByUser(String username) {
        return this.accessoryRepository.getAccessoriesByUser(username)
                .stream()
                .map(accessory -> this.modelMapper.map(accessory,AccessoryViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void removeAnimalFromUsers(Animal animalById) {
        List<User> allUsers = userRepository.getAllUsers();
        for (User allUser : allUsers) {
            allUser.getLikedAnimals().remove(animalById);
        }
    }

    @Override
    public void removeAccessoryFromUsers(Accessory accessoryById) {
        List<User> allUsers = userRepository.getAllUsers();
        for (User allUser : allUsers) {
            allUser.getLikedAccessories().remove(accessoryById);
        }
    }

    @Override
    public void removeUser(String id) {
        List<User> allUsersInDB = getAllUsersInDB();
        List<Animal> animalByUser = animalRepository.getAnimalByUserId(id);
        for (Animal animal : animalByUser) {
            for (User user : allUsersInDB) {
                user.getLikedAnimals().remove(animal);
                userRepository.saveAndFlush(user);
            }
            animalRepository.deleteById(animal.getId());
        }
        List<Accessory> accessoriesByUser = accessoryRepository.getAccessoryByUserId(id);
        for (Accessory accessory : accessoriesByUser) {

            for (User user : allUsersInDB) {
                user.getLikedAccessories().remove(accessory);
                userRepository.saveAndFlush(user);
            }
            accessoryRepository.deleteById(accessory.getId());
        }
        List<Store> storeByUser = storeRepository.getStoreByUserId(id);
        for (Store store : storeByUser) {
            storeRepository.deleteById(store.getId());

        }


        this.userRepository.deleteById(id);

    }


    @Override
    public void updateProfile(UserUpdateProfileModel userUpdateProfileModel) {
        User user = this.userRepository.findByUsername(userUpdateProfileModel.getUsername());
        user.setEmail(userUpdateProfileModel.getEmail());
        user.setPhoneNumber(userUpdateProfileModel.getPhoneNumber());
        user.setFullName(userUpdateProfileModel.getFullName());
        this.userRepository.save(user);
    }

    @Override
    public List<User> getAllUsersInDB() {
        return this.userRepository.getAllUsers();
    }

    @Override
    public List<User> getAllUsersForUserControl(){
        return this.userRepository.getAllUsersByAccessoryType();
    }

    @Override
    public void saveUrl(String username,String fileName) {
        User byUsername = this.userRepository.findByUsername(username);
        byUsername.setUrlProfilePicture("https://jdanimalsworld.s3.eu-central-1.amazonaws.com/" + fileName);
        this.userRepository.saveAndFlush(byUsername);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(s);
    }
}

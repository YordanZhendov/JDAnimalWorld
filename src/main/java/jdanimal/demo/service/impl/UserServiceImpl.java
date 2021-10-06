package jdanimal.demo.service.impl;

import jdanimal.demo.data.Animal;
import jdanimal.demo.data.DTO.UserRegisterDTO;
import jdanimal.demo.data.enums.UserStatus;
import jdanimal.demo.service.models.UserAnimalUploadModel;
import jdanimal.demo.data.DTO.UserLoginDTO;
import jdanimal.demo.data.DTO.UserProfileDTO;
import jdanimal.demo.data.Role;
import jdanimal.demo.data.User;
import jdanimal.demo.repository.AnimalRepository;
import jdanimal.demo.repository.UserRepository;
import jdanimal.demo.service.RoleService;
import jdanimal.demo.service.UserService;
import jdanimal.demo.service.UserValidationSerivce;
import jdanimal.demo.data.DTO.UserAnimalUploadDTO;
import jdanimal.demo.service.models.UserUpdateProfileModel;
import jdanimal.demo.service.views.AnimalViewModel;
import jdanimal.demo.util.ValidationUtil;
import jdanimal.demo.service.views.UserProfileViewModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AnimalRepository animalRepository;
    private final UserValidationSerivce userValidationSerivce;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;
    private final RoleService roleService;
    private final ValidationUtil validationUtil;


    @Override
    public void register(UserRegisterDTO userRegisterDTO) {
        if(!validationUtil.isValid(userRegisterDTO)){
            return;
        }

        User user=this.modelMapper.map(userRegisterDTO,User.class);

        if(!userValidationSerivce.checkUser(user)){
            return;
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
        user.setStatus(UserStatus.ACTIVE);
        this.userRepository.save(user);

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
    public List<AnimalViewModel> getAllAnimalsByUser(String id) {
        return this.animalRepository.getAnimalByUser(id)
                .stream()
                .map(animal -> this.modelMapper.map(animal,AnimalViewModel.class))
                .collect(Collectors.toList());
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
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(s);
    }
}

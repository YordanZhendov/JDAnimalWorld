package jdanimal.demo.service.impl;

import jdanimal.demo.data.Animal;
import jdanimal.demo.service.models.UserAnimalUploadModel;
import jdanimal.demo.data.DTO.UserLoginDTO;
import jdanimal.demo.data.DTO.UserProfileDTO;
import jdanimal.demo.data.DTO.UserRegistrationDTO;
import jdanimal.demo.data.Role;
import jdanimal.demo.data.User;
import jdanimal.demo.repository.AnimalRepository;
import jdanimal.demo.repository.UserRepository;
import jdanimal.demo.service.RoleService;
import jdanimal.demo.service.UserService;
import jdanimal.demo.service.UserValidationSerivce;
import jdanimal.demo.data.DTO.UserAnimalUploadDTO;
import jdanimal.demo.util.ValidationUtil;
import jdanimal.demo.service.models.UserProfileModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
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
    public void register(UserRegistrationDTO userRegistrationDTO) {
        if(!validationUtil.isValid(userRegistrationDTO)){
            return;
        }

        User user=modelMapper.map(userRegistrationDTO,User.class);

        if(!userValidationSerivce.checkUser(user)){
            return;
        }

        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        user.setConfirmPassword(this.bCryptPasswordEncoder.encode(user.getConfirmPassword()));

        if(userRepository.count() == 0){
            roleService.seedRoles();
            user.setAuthorities(this.roleService.findAllRoles()
                    .stream().map(r->modelMapper
                            .map(r,Role.class))
                    .collect(Collectors.toSet()));
        }else {

            user.setAuthorities(new LinkedHashSet<>());
            user.getAuthorities().add(modelMapper.map(roleService.findByAuthority("GUEST"),Role.class));
        }

        this.userRepository.save(user);

    }

    @Override
    public User validUser(UserLoginDTO userLoginDTO) {
        userLoginDTO.setPassword(this.bCryptPasswordEncoder.encode(userLoginDTO.getPassword()));
        User userLogin = modelMapper.map(userLoginDTO, User.class);
        return this.userRepository.findByUsernameAndPassword(userLogin.getUsername(), userLogin.getPassword()).orElse(null);
    }

    @Override
    public UserProfileModel findByUsername(String currentUserName) {
        User byUsername = this.userRepository.findByUsername(currentUserName);
        UserProfileDTO userProfileDTO = modelMapper.map(byUsername, UserProfileDTO.class);
        return modelMapper.map(userProfileDTO,UserProfileModel.class);
    }

    @Override
    public void uploadAnimal(UserAnimalUploadModel userAnimalUploadBindingModel, UserProfileModel userProfileInfo) {
        UserAnimalUploadDTO mappedAnimal = this.modelMapper.map(userAnimalUploadBindingModel, UserAnimalUploadDTO.class);
        Animal mappedAnimalEntity = this.modelMapper.map(mappedAnimal, Animal.class);
        User byUsername = this.userRepository.findByUsername(userProfileInfo.getUsername());

        mappedAnimalEntity.setUser(byUsername);
        this.animalRepository.saveAndFlush(mappedAnimalEntity);
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(s);
    }
}

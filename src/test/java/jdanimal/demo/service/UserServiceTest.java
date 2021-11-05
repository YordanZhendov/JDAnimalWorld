package jdanimal.demo.service;

import jdanimal.demo.DemoApplication;
import jdanimal.demo.data.Animal;
import jdanimal.demo.data.Role;
import jdanimal.demo.data.User;
import jdanimal.demo.repository.AnimalRepository;
import jdanimal.demo.repository.RoleRepository;
import jdanimal.demo.repository.UserRepository;
import jdanimal.demo.service.views.AnimalViewModel;
import jdanimal.demo.service.views.RoleServiceViewModel;
import jdanimal.demo.service.views.UserProfileViewModel;
import jdanimal.demo.web.binding.UserLoginBinding;
import jdanimal.demo.web.binding.UserRegistrationBinding;
import jdanimal.demo.web.binding.UserUpdateProfileBinding;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    User user2;

    @BeforeEach
    void setUp() {

        roleService.seedRoles();

        User user = new User();
        user.setUsername("Joro");
        user.setPassword("12345");
        user.setConfirmPassword("12345");
        user.setEmail("jordan@abv.bg");
        user.setPostcode("4445");
        user.setPhoneNumber("0856258851");
        user.setPolicyAgree(true);
        user.setUserStatus(true);

        user2 = new User();
        user2.setUsername("Desi");
        user2.setPassword("12345");
        user2.setConfirmPassword("12345");
        user2.setEmail("desi@abv.bg");
        user2.setPostcode("5555");
        user2.setPhoneNumber("0856254851");
        user2.setPolicyAgree(true);
        user2.setUserStatus(true);

        userRepository.save(user);
        userRepository.save(user2);


    }


    @Test
    void register() {
        UserRegistrationBinding userRegistrationBinding = new UserRegistrationBinding();
        userRegistrationBinding.setUsername("Yordan");
        userRegistrationBinding.setPassword("12345");
        userRegistrationBinding.setConfirmPassword("12345");
        userRegistrationBinding.setEmail("yordan@abv.bg");
        userRegistrationBinding.setPostcode("5555");
        userRegistrationBinding.setPhoneNumber("0844254851");
        userRegistrationBinding.setPolicyAgree(true);
        assertTrue(userService.register(userRegistrationBinding));
    }

    @Test
    void findByUsername() {
        assertNotNull(userService.findByUsername("Desi"));

    }

    @Test
    void getAllAnimalsByUserName() {
        Animal animal=new Animal();
        animal.setFoodOfAnimal("Meat");
        animal.setGamesOfAnimal("Ball");
        animal.setTypeOfAnimal("Golden Retriver");
        animal.setAgeOfAnimal("3");
        animal.setKilogramsOfAnimal("40");
        animal.setUser(user2);
        animalRepository.save(animal);
        assertEquals(1,userService.getAllAnimalsByUserName("Desi").size());
    }

    @Test
    void updateProfile() {
        UserUpdateProfileBinding userUpdateProfileBinding= new UserUpdateProfileBinding();
        userUpdateProfileBinding.setUsername("Desi");
        userUpdateProfileBinding.setEmail("updatedInfo@abv.bg");
        userUpdateProfileBinding.setPhoneNumber("0856254851");
        userService.updateProfile(userUpdateProfileBinding);

        UserProfileViewModel byUsername = userService.findByUsername(userUpdateProfileBinding.getUsername());

        boolean changed=false;
        if(byUsername.getEmail().equals("updatedInfo@abv.bg")){
            changed=true;
        }

        assertTrue(changed);
    }

    @Test
    void saveUrl() {
        //TOdo TO BE CONTINUED!
    }

    @Test
    void removeAnimalFromUsers() {
        //TOdo TO BE CONTINUED!
    }

    @Test
    void getAllAccessoriesByUser() {
        //TOdo TO BE CONTINUED!
    }

    @Test
    void removeAccessoryFromUsers() {
        //TOdo TO BE CONTINUED!
    }

    @Test
    void removeUser() {
        //TOdo TO BE CONTINUED!
    }

    @Test
    void getAllUsersForUserControl() {
//        assertEquals(1,userService.getAllUsersForUserControl().size());
        //TOdo TO BE CONTINUED!
    }

    @Test
    void sendEmail() {
        assertTrue(userService.sendEmail());
    }

    @Test
    void suspendUser() {
        assertTrue(userService.suspendUser(user2.getId()));
    }

    @Test
    void activateUser() {
        assertTrue(userService.activateUser(user2.getId()));
    }

    @Test
    void updateCash() {
        assertTrue(userService.updateCash());

    }

    @AfterEach
    void tearDown() {
        animalRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }
}
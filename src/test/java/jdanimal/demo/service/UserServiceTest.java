package jdanimal.demo.service;

import jdanimal.demo.DemoApplication;
import jdanimal.demo.data.Accessory;
import jdanimal.demo.data.Animal;
import jdanimal.demo.data.Role;
import jdanimal.demo.data.User;
import jdanimal.demo.repository.AccessoryRepository;
import jdanimal.demo.repository.AnimalRepository;
import jdanimal.demo.repository.RoleRepository;
import jdanimal.demo.repository.UserRepository;
import jdanimal.demo.service.views.AccessoryViewModel;
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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
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
    private AccessoryRepository accessoryRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    User user1;
    User user2;
    Animal animal1;
    Accessory accessory1;

    @BeforeEach
    void setUp() {

        roleService.seedRoles();

        RoleServiceViewModel adminRole = roleService.findByAuthority("ADMIN");
        Role adminR = modelMapper.map(adminRole, Role.class);
        RoleServiceViewModel userRole = roleService.findByAuthority("USER");
        Role userR = modelMapper.map(userRole, Role.class);
        RoleServiceViewModel anonymRole = roleService.findByAuthority("GUEST");
        Role anonymR = modelMapper.map(anonymRole, Role.class);

        User guest=new User();
        guest.setUsername("Guest");
        guest.setPassword("12345");
        guest.setConfirmPassword("12345");
        guest.setEmail("guest@abv.bg");
        guest.setPostcode("4445");
        guest.setPhoneNumber("1116258851");
        guest.setPolicyAgree(true);
        guest.setUserStatus(true);
        guest.setAuthorities(Set.of(adminR,userR,anonymR));
        user1 = userRepository.save(guest);

        Animal animal=new Animal();
        animal.setFoodOfAnimal("Meat");
        animal.setGamesOfAnimal("Ball");
        animal.setTypeOfAnimal("Golden Retriver");
        animal.setAgeOfAnimal(3);
        animal.setKilogramsOfAnimal(40);
        animal1 = animalRepository.save(animal);

        Accessory accessory=new Accessory();
        accessory.setAccessoryPrice(new BigDecimal(5));
        accessory.setDescription("Never used");
        accessory.setDaysUsed(0);
        accessory.setAvailableTill("22-2-2021");
        accessory.setUser(guest);
        accessory1 = accessoryRepository.save(accessory);

        user2 = new User();
        user2.setUsername("Desi");
        user2.setPassword("12345");
        user2.setConfirmPassword("12345");
        user2.setEmail("desi@abv.bg");
        user2.setPostcode("5555");
        user2.setPhoneNumber("0856254851");
        user2.setPolicyAgree(true);
        user2.setUserStatus(true);
        user2.setAuthorities(Set.of(userR));
        user2.setLikedAnimals(Set.of(animal1));
        user2.setLikedAccessories(Set.of(accessory1));

        User user = new User();
        user.setUsername("Joro");
        user.setPassword("12345");
        user.setConfirmPassword("12345");
        user.setEmail("jordan@abv.bg");
        user.setPostcode("4445");
        user.setPhoneNumber("0856258851");
        user.setPolicyAgree(true);
        user.setUserStatus(true);
        user.setAuthorities(Set.of(userR));
        user.setLikedAnimals(Set.of(animal1));
        user.setLikedAccessories(Set.of(accessory1));

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
        animal.setAgeOfAnimal(3);
        animal.setKilogramsOfAnimal(40);
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
        assertTrue(userService.saveUrl(user2.getUsername(),"Yordan Zhendov"));
    }

    @Test
    void removeLikedAnimalFromUsers() {
        assertTrue(userService.removeLikedAnimalFromUsers(animal1));
    }

    @Test
    void removeLikedAccessoryFromUsers() {
        assertTrue(userService.removeLikedAccessoryFromUsers(accessory1));
    }

    @Test
    void getAllAccessoriesByUser() {
        List<AccessoryViewModel> allAccessoriesByUserName = userService.getAllAccessoriesByUserName(user1.getUsername());
        assertEquals(1,allAccessoriesByUserName.size());
    }


    @Test
    void removeUser() {
        assertTrue(userService.removeUser(user2.getId()));
    }

    @Test
    void getAllUsersForUserControl() {
        assertEquals(2,userService.getAllUsersForUserControl().size());
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
        accessoryRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }
}
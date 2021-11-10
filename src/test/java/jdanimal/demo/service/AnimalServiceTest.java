package jdanimal.demo.service;

import jdanimal.demo.DemoApplication;
import jdanimal.demo.data.Animal;
import jdanimal.demo.data.User;
import jdanimal.demo.repository.AnimalRepository;
import jdanimal.demo.repository.UserRepository;
import jdanimal.demo.service.views.AnimalViewModel;
import jdanimal.demo.service.views.UserProfileViewModel;
import jdanimal.demo.web.binding.UserAnimalUploadBinding;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
class AnimalServiceTest {

    @Autowired
    private AnimalService animalService;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    User savedU;
    Animal upload;
    UserAnimalUploadBinding savedAn;
    UserProfileViewModel mappedUser;

    @BeforeEach
    void setUp() {

        User user1 = new User();
        user1.setUsername("Ivan");
        user1.setPassword("12345");
        user1.setConfirmPassword("12345");
        user1.setEmail("ivan@abv.bg");
        user1.setPostcode("4455");
        user1.setPhoneNumber("0336258851");
        user1.setPolicyAgree(true);
        user1.setUserStatus(true);
        userRepository.save(user1);

        User user = new User();
        user.setUsername("Joro");
        user.setPassword("12345");
        user.setConfirmPassword("12345");
        user.setEmail("jordan@abv.bg");
        user.setPostcode("4445");
        user.setPhoneNumber("0856258851");
        user.setPolicyAgree(true);
        user.setUserStatus(true);
        savedU = userRepository.save(user);
        mappedUser = this.modelMapper.map(savedU, UserProfileViewModel.class);

        savedAn=new UserAnimalUploadBinding();
        savedAn.setFoodOfAnimal("Meat");
        savedAn.setGamesOfAnimal("Ball");
        savedAn.setTypeOfAnimal("Golden Retriver");
        savedAn.setAgeOfAnimal(3);
        savedAn.setKilogramsOfAnimal(40);
        upload = animalService.uploadAnimal(savedAn, mappedUser);
    }

    @Test
    void uploadAnimal() {
        assertNotNull(upload);
    }

    @Test
    void getAllAnimals() {
        List<AnimalViewModel> allAnimals = animalService.getAllAnimals();
        assertEquals(1,allAnimals.size());
    }

    @Test
    void removeAnimal() {
        assertTrue(animalService.removeAnimal(upload.getId()));
    }

    @Test
    void saveUrlAnimal() {
        assertTrue(animalService.saveUrlAnimal(upload.getId(),"Kurzhaardackel"));
    }

    @Test
    void addLikedAnimalToTheCurrentUser() {
        assertTrue(animalService.addLikedAnimalToTheCurrentUser(upload.getId(),"Ivan"));
        assertTrue(animalService.addLikedAnimalToTheCurrentUser(upload.getId(),"Joro"));
    }

    @Test
    void disLikedAnimalToTheCurrentUser() {
        assertTrue(animalService.disLikedAnimalToTheCurrentUser(upload.getId(),"Ivan"));
        assertTrue(animalService.disLikedAnimalToTheCurrentUser(upload.getId(),"Joro"));
    }

    @Test
    void updateAnimalCash() {
        assertTrue(animalService.updateAnimalCash());
    }

    @AfterEach
    void tearDown() {
        animalRepository.deleteAll();
        userRepository.deleteAll();
    }
}
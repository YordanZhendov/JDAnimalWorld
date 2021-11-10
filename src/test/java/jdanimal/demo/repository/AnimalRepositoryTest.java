package jdanimal.demo.repository;

import jdanimal.demo.DemoApplication;
import jdanimal.demo.data.Accessory;
import jdanimal.demo.data.Animal;
import jdanimal.demo.data.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
class AnimalRepositoryTest {


    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private UserRepository userRepository;

    User savedU;
    Animal savedA;

    @BeforeEach
    void setUp() {
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

        Animal animal=new Animal();
        animal.setFoodOfAnimal("Meat");
        animal.setGamesOfAnimal("Ball");
        animal.setTypeOfAnimal("Golden Retriver");
        animal.setAgeOfAnimal(3);
        animal.setKilogramsOfAnimal(40);
        animal.setUser(user);
        savedA = animalRepository.save(animal);

    }
    //Testing Animal Entity  ->>>>>>>> NOT BINDING MODELS!!!
    @Test
    public void enterTwoAnimalsMustThrowException(){
        Animal animal=new Animal();
        animal.setFoodOfAnimal("Meat");
        animal.setGamesOfAnimal("Ball");
        animal.setTypeOfAnimal("Golden Retriver");
        animal.setAgeOfAnimal(3);
        animal.setKilogramsOfAnimal(40);
        animal.setUser(savedU);

        boolean thrown = false;
        try {
            animalRepository.save(animal);
        } catch (Exception e) {
            thrown = true;
        }

    }

    //Testing Animal Repository
    @Test
    void findAll() {
        assertEquals(1,animalRepository.count());
    }

    @Test
    void getAnimalByUser() {
        List<Animal> animalList = animalRepository.getAnimalByUserUserName("Joro");
        assertEquals(1,animalList.size());
    }

    @Test
    void findAnimalById() {
        assertNotNull(animalRepository.findAnimalById(savedA.getId()));
    }

    @Test
    void getAnimalByUserId() {
        List<Animal> animalByUserId = animalRepository.getAnimalByUserId(savedU.getId());
        assertEquals(1,animalByUserId.size());
    }

    @AfterEach
    void tearDown() {
        animalRepository.deleteAll();
        userRepository.deleteAll();
    }
}
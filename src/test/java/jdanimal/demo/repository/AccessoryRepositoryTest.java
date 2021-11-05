package jdanimal.demo.repository;

import jdanimal.demo.DemoApplication;
import jdanimal.demo.data.Accessory;
import jdanimal.demo.data.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
class AccessoryRepositoryTest {

    @Autowired
    private AccessoryRepository accessoryRepository;
    @Autowired
    private UserRepository userRepository;

    Accessory savedA;
    User savedU;

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

        Accessory accessory=new Accessory();
        accessory.setAccessoryPrice(new BigDecimal(5));
        accessory.setDescription("Never used");
        accessory.setDaysUsed(0);
        accessory.setAvailableTill("22-2-2021");
        accessory.setUser(user);
        savedA = accessoryRepository.save(accessory);
    }

    //Testing Accessory Entity  ->>>>>>>> NOT BINDING MODELS!!!
    @Test
    public void enterTwoAccessoriesMustThrowException(){
        Accessory accessory=new Accessory();
        accessory.setAccessoryPrice(new BigDecimal(5));
        accessory.setDescription("Never used");
        accessory.setDaysUsed(0);
        accessory.setAvailableTill("22-2-2021");
        accessory.setUser(savedU);

        boolean thrown = false;
        try {
           accessoryRepository.save(accessory);
        } catch (Exception e) {
            thrown = true;
        }

    }

    //Testing Accessory Repository
    @Test
    void findAll() {
        assertEquals(1,accessoryRepository.count());
    }

    @Test
    void checkIfCanGetAccessoriesByUser() {
        List<Accessory> accessoriesByUsername = accessoryRepository.getAccessoriesByUserUsername("Joro");
        assertEquals(1,accessoriesByUsername.size());
    }

    @Test
    void findAccessoryById() {
        assertNotNull(accessoryRepository.findAccessoryById(savedA.getId()));
    }

    @Test
    void getAccessoryByUserId() {
        List<Accessory> accessoriesByUsername = accessoryRepository.getAccessoryByUserId(savedU.getId());
        assertEquals(1,accessoriesByUsername.size());

    }

    @AfterEach
    void tearDown() {
        accessoryRepository.deleteAll();
        userRepository.deleteAll();
    }

}
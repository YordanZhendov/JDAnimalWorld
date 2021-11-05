package jdanimal.demo.service;

import jdanimal.demo.DemoApplication;
import jdanimal.demo.data.User;
import jdanimal.demo.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
class UserValidationServiceTest {

    @Autowired
    private UserValidationService userValidationService;
    @Autowired
    private UserRepository userRepository;

    @Test
    void checkUser() {
        User user = new User();
        user.setUsername("Joro");
        user.setPassword("12345");
        user.setConfirmPassword("12345");
        user.setEmail("jordan@abv.bg");
        user.setPostcode("4445");
        user.setPhoneNumber("0856258851");
        user.setPolicyAgree(true);
        user.setUserStatus(true);
        assertTrue(userValidationService.checkUser(user));
    }

    @Test
    void checkUserAlreadyExists(){
        User user = new User();
        user.setUsername("Joro");
        user.setPassword("12345");
        user.setConfirmPassword("12345");
        user.setEmail("jordan@abv.bg");
        user.setPostcode("4445");
        user.setPhoneNumber("0856258851");
        user.setPolicyAgree(true);
        user.setUserStatus(true);
        userRepository.save(user);
        assertFalse(userValidationService.checkUser(user));

    }

    @AfterEach
    public void tearDown(){
        userRepository.deleteAll();
    }
}
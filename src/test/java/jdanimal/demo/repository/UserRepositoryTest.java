package jdanimal.demo.repository;

import jdanimal.demo.DemoApplication;
import jdanimal.demo.data.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        User user = new User();
        user.setUsername("Joro");
        user.setPassword("12345");
        user.setConfirmPassword("12345");
        user.setEmail("jordan@abv.bg");
        user.setPostcode("4445");
        user.setPhoneNumber("0856258851");
        user.setPolicyAgree(true);
        user.setUserStatus(true);

        User user2 = new User();
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

    //Testing User Entity  ->>>>>>>> NOT BINDING MODELS!!!
    @Test
    public void enterTwoUsersMustThrowException(){
        User user = new User();
        user.setUsername("Joro");
        user.setPassword("12345");
        user.setConfirmPassword("12345");
        user.setEmail("jordan@abv.bg");
        user.setPostcode("4445");
        user.setPhoneNumber("0856258851");
        user.setPolicyAgree(true);
        user.setUserStatus(true);

        boolean thrown = false;
        try {
            userRepository.save(user);
        } catch (Exception e) {
            thrown = true;
        }

    }

    //Testing User Repository
    @Test
    public void checkIfUserExistsByUsername(){

        boolean exists = userRepository.existsByUsername("Desi");
        assertTrue(exists);
    }

    @Test
    public void checkIfUserExistsByEmail() {

        boolean exists = userRepository.existsByEmail("desi@abv.bg");
        assertTrue(exists);
    }

    @Test
    public void checkIfUserExistsByPhoneNumber() {
        boolean exists = userRepository.existsAllByPhoneNumber("0856258851");
        assertTrue(exists);
    }

    @Test
    public void checkIfCanFindUserByUsername() {

        User userFoundByUsername = userRepository.findByUsername("Desi");
        assertEquals("desi@abv.bg",userFoundByUsername.getEmail());
    }

    @Test
    public void checkIfCanFindUserById() {

        User userFoundByUsername = userRepository.findByUsername("Desi");
        User userFoundById = userRepository.findById(userFoundByUsername.getId()).orElse(null);
        if(userFoundById != null){
            assertEquals(userFoundByUsername.getId(),userFoundById.getId());
        }
    }
    @Test
    public void checkIfCanFindUserByUsernameAndPassword() {

        User userFoundByUsername = userRepository.findByUsername("Desi");
        User userFoundById = userRepository.findByUsernameAndPassword(userFoundByUsername.getUsername(),userFoundByUsername.getPassword()).orElse(null);
        if(userFoundById != null){
            assertEquals(userFoundByUsername.getId(),userFoundById.getId());
        }
    }

    @Test
    public void checkIfCanGetAllUsersInDB() {

        List<User> allUsers = userRepository.getAllUsersInDB();
        assertEquals(userRepository.count(),allUsers.size());
    }

    @AfterEach
    public void tearDown(){
        userRepository.deleteAll();
    }

}


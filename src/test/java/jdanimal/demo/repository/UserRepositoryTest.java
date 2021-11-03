package jdanimal.demo.repository;

import jdanimal.demo.DemoApplication;
import jdanimal.demo.data.User;
import org.junit.jupiter.api.AfterEach;
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

    //Testing User Entity  ->>>>>>>> NOT BINDING MODELS!!!

    @Test
    public void registerUser() {
        User user = new User();
        user.setUsername("Joro");
        user.setFullName("Joro Ivanov");
        user.setPassword("12345");
        user.setConfirmPassword("12345");
        user.setEmail("jordan@abv.bg");
        user.setPostcode("4445");
        user.setPhoneNumber("0856258851");
        user.setCity("Varna");
        user.setCountry("Bulgaria");
        user.setPolicyAgree(true);
        user.setUserStatus(true);
        userRepository.save(user);

        assertEquals(1,userRepository.count());

    }

    @Test
    public void enterTwoUsersMustThrowException(){
        User user = new User();
        user.setUsername("Joro");
        user.setFullName("Joro Ivanov");
        user.setPassword("12345");
        user.setConfirmPassword("12345");
        user.setEmail("jordan@abv.bg");
        user.setPostcode("4445");
        user.setPhoneNumber("0856258851");
        user.setCity("Varna");
        user.setCountry("Bulgaria");
        user.setPolicyAgree(true);
        user.setUserStatus(true);

        User user2 = new User();
        user2.setUsername("Joro");
        user2.setFullName("Joro Ivanov");
        user2.setPassword("12345");
        user2.setConfirmPassword("12345");
        user2.setEmail("jordan@abv.bg");
        user2.setPostcode("4445");
        user2.setPhoneNumber("0856258851");
        user2.setCity("Varna");
        user2.setCountry("Bulgaria");
        user2.setPolicyAgree(true);
        user2.setUserStatus(true);


        boolean thrown = false;
            userRepository.save(user);
        try {
            userRepository.save(user2);
        } catch (Exception e) {
            thrown = true;
        }

    }

    @Test
    public void enterTwoDifferentUsers(){
        User user = new User();
        user.setUsername("Joro");
        user.setFullName("Joro Ivanov");
        user.setPassword("12345");
        user.setConfirmPassword("12345");
        user.setEmail("jordan@abv.bg");
        user.setPostcode("4445");
        user.setPhoneNumber("0856258851");
        user.setCity("Varna");
        user.setCountry("Bulgaria");
        user.setPolicyAgree(true);
        user.setUserStatus(true);

        User user2 = new User();
        user2.setUsername("Desi");
        user2.setFullName("Desi Ivanova");
        user2.setPassword("12345");
        user2.setConfirmPassword("12345");
        user2.setEmail("desi@abv.bg");
        user2.setPostcode("5555");
        user2.setPhoneNumber("0856254851");
        user2.setCity("Varna");
        user2.setCountry("Bulgaria");
        user2.setPolicyAgree(true);
        user2.setUserStatus(true);

        userRepository.save(user);
        userRepository.save(user2);


    }

    //Testing User Repository

    @Test
    public void checkIfUserExistsByUsername(){
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

        boolean exists = userRepository.existsByUsername("Desi");

        assertTrue(exists);
    }

    @Test
    public void checkIfUserExistsByEmail() {
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

        boolean exists = userRepository.existsByEmail("desi@abv.bg");

        assertTrue(exists);
    }

    @Test
    public void checkIfUserExistsByPhoneNumber() {
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

        boolean exists = userRepository.existsAllByPhoneNumber("0856258851");
        assertTrue(exists);
    }

    @Test
    public void checkIfCanFindUserByUsername() {
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
        User userFoundByUsername = userRepository.findByUsername("Desi");
        assertEquals("desi@abv.bg",userFoundByUsername.getEmail());
    }

    @Test
    public void checkIfCanFindUserById() {
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
        User userFoundByUsername = userRepository.findByUsername("Desi");
        User userFoundById = userRepository.findById(userFoundByUsername.getId()).orElse(null);
        if(userFoundById != null){
            assertEquals(userFoundByUsername.getId(),userFoundById.getId());
        }
    }
    @Test
    public void checkIfCanFindUserByUsernameAndPassword() {
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
        User userFoundByUsername = userRepository.findByUsername("Desi");
        User userFoundById = userRepository.findByUsernameAndPassword(userFoundByUsername.getUsername(),userFoundByUsername.getPassword()).orElse(null);
        if(userFoundById != null){
            assertEquals(userFoundByUsername.getId(),userFoundById.getId());
        }
    }

    @Test
    public void checkIfCanGetAllUsersInDB() {
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

        List<User> allUsers = userRepository.getAllUsersInDB();
        assertEquals(userRepository.count(),allUsers.size());
    }

    @AfterEach
    public void tearDown(){
        userRepository.deleteAll();
    }

}


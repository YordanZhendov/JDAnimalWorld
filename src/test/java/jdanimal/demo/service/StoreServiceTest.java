package jdanimal.demo.service;

import jdanimal.demo.DemoApplication;
import jdanimal.demo.data.Store;
import jdanimal.demo.data.User;
import jdanimal.demo.repository.StoreRepository;
import jdanimal.demo.repository.UserRepository;
import jdanimal.demo.web.binding.UserStoreUploadBinding;
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
class StoreServiceTest {

    @Autowired
    private StoreService storeService;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private UserRepository userRepository;

    User savedU;
    Store savedS;

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

        Store store=new Store();
        store.setUser(user);
        store.setDescription("Best Supermarket ever!!!!");
        store.setLocationPath("<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d186126.44345323008!2d27.80282489072861!3d43.204755644600745!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x40a4538baaf3d7a1%3A0x5727941c71a58b7c!2z0JLQsNGA0L3QsA!5e0!3m2!1sbg!2sbg!4v1636024828658!5m2!1sbg!2sbg\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\"></iframe>");
        savedS = storeRepository.save(store);
    }


    @Test
    void uploadStore() {
        UserStoreUploadBinding userStoreUploadBinding=new UserStoreUploadBinding();
        userStoreUploadBinding.setDescription("Best Supermarket ever!!!!");
        userStoreUploadBinding.setLocationPath("<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d186126.44345323008!2d27.80282489072861!3d43.204755644600745!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x40a4538baaf3d7a1%3A0x5727941c71a58b7c!2z0JLQsNGA0L3QsA!5e0!3m2!1sbg!2sbg!4v1636024828658!5m2!1sbg!2sbg\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\"></iframe>");
        storeService.uploadStore(userStoreUploadBinding,"Joro");
        assertEquals(2,storeRepository.count());

    }

    @Test
    void getAllStores() {
        assertEquals(1,storeService.getAllStores().size());
    }

    @Test
    void getAllStoresByUser() {
        assertEquals(1,storeService.getAllStoresByUser("Joro").size());
    }

    @Test
    void removeStore() {
        assertTrue(storeService.removeStore(savedS.getId()));
        assertEquals(0,storeRepository.count());
    }

    @Test
    void updateStoreCash() {
        assertTrue(storeService.updateStoreCash());
    }


    @AfterEach
    void tearDown() {
        storeRepository.deleteAll();
        userRepository.deleteAll();
    }
}
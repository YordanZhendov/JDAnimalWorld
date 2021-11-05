package jdanimal.demo.service;

import jdanimal.demo.DemoApplication;
import jdanimal.demo.data.Accessory;
import jdanimal.demo.data.User;
import jdanimal.demo.repository.AccessoryRepository;
import jdanimal.demo.repository.UserRepository;
import jdanimal.demo.service.views.AccessoryViewModel;
import jdanimal.demo.service.views.UserProfileViewModel;
import jdanimal.demo.web.binding.UserAccessoryUploadBinding;
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

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
class AccessoryServiceTest {

    @Autowired
    private AccessoryService accessoryService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AccessoryRepository accessoryRepository;

    User savedU;
    Accessory upload;
    UserAccessoryUploadBinding savedAc;
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

        savedAc =new UserAccessoryUploadBinding();
        savedAc.setAccessoryPrice(new BigDecimal(5));
        savedAc.setDescription("Never used");
        savedAc.setDaysUsed(0);
        savedAc.setAvailableTill("22-2-2021");
        savedAc.setAccessoryName("Ball");

        upload = accessoryService.upload(savedAc, mappedUser);
    }

    @Test
    void upload() {
        assertNotNull(upload);
    }

    @Test
    void removeAccessory() {
        assertTrue(accessoryService.removeAccessoryById(upload.getId()));
    }

    @Test
    void getAllAccessories() {
        List<AccessoryViewModel> allAccessories = accessoryService.getAllAccessories();
        assertEquals(1,allAccessories.size());
    }

    @Test
    void saveUrlAccessory() {
        assertTrue(accessoryService.saveUrlAccessory(upload.getId(),"df63d0e07528eb159a7209a3cd354ef7"));
    }



    @Test
    void addLikedAccessoryTotheCurrentUser() {
        assertTrue(accessoryService.addLikedAccessoryTotheCurrentUser(upload.getId(),"Ivan"));
        assertTrue(accessoryService.addLikedAccessoryTotheCurrentUser(upload.getId(),"Joro"));

    }

    @Test
    void disLikedAccessoryTotheCurrentUser() {
        assertTrue(accessoryService.disLikedAccessoryTotheCurrentUser(upload.getId(),"Ivan"));
        assertTrue(accessoryService.disLikedAccessoryTotheCurrentUser(upload.getId(),"Joro"));
    }

    @Test
    void updateAccessoryCash() {
        assertTrue(accessoryService.updateAccessoryCash());
    }


    @AfterEach
    void tearDown() {
        accessoryRepository.deleteAll();
        userRepository.deleteAll();
    }
}
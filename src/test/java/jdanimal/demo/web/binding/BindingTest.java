package jdanimal.demo.web.binding;

import jdanimal.demo.DemoApplication;
import jdanimal.demo.data.EncyclopediaAnimal;
import jdanimal.demo.data.Store;
import jdanimal.demo.data.enums.AnimalType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import static org.junit.jupiter.api.Assertions.*;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.util.Set;

@SpringBootTest(classes = DemoApplication.class)
public class BindingTest {

    @Autowired
    private LocalValidatorFactoryBean validator;

    @Test
    void uploadAnimal() {
        UserAnimalUploadBinding animalDTO =UserAnimalUploadBinding.builder()
        .foodOfAnimal("Ivan")
        .gamesOfAnimal("Ball")
        .typeOfAnimal("Golden Retriver")
        .ageOfAnimal(3)
        .kilogramsOfAnimal(40).build();
        Set<ConstraintViolation<UserAnimalUploadBinding>> animalBinding = validator.validate(animalDTO);
        assertEquals(2,animalBinding.size());
    }

    @Test
    void uploadAccessory(){
        UserAccessoryUploadBinding accessoryDTO=UserAccessoryUploadBinding.builder()
        .accessoryPrice(new BigDecimal(5))
        .description("Never used")
        .daysUsed(0)
        .availableTill("22-2-2021")
        .build();
        Set<ConstraintViolation<UserAccessoryUploadBinding>> accessoryBinding = validator.validate(accessoryDTO);
        assertEquals(1,accessoryBinding.size());
    }
    @Test
    void uploadEncyclopediaAnimal(){
        EncyclopediaAnimalBinding encyclopediaDTO=EncyclopediaAnimalBinding.builder()
        .typeOfAnimal(AnimalType.Dog)
        .animalPhotoUrl("https://upload.wikimedia.org/wikipedia/commons/8/82/Golden_Retriever_standing_Tucker.jpg")
        .build();
        Set<ConstraintViolation<EncyclopediaAnimalBinding>> encyclopediaBinding = validator.validate(encyclopediaDTO);
        assertEquals(1,encyclopediaBinding.size());
    }

    @Test
    void uploadStore(){
        UserStoreUploadBinding storeDTO= UserStoreUploadBinding.builder()
        .description("Best Supermarket ever!!!!")
        .locationPath("<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d186126.44345323008!2d27.80282489072861!3d43.204755644600745!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x40a4538baaf3d7a1%3A0x5727941c71a58b7c!2z0JLQsNGA0L3QsA!5e0!3m2!1sbg!2sbg!4v1636024828658!5m2!1sbg!2sbg\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\"></iframe>")
        .build();

        Set<ConstraintViolation<UserStoreUploadBinding>> storeBinding = validator.validate(storeDTO);
        assertEquals(1,storeBinding.size());
    }

    @Test
    void uploadUser(){
        UserRegistrationBinding userRegisterDTO= UserRegistrationBinding.builder()
        .password("12345")
        .confirmPassword("12345")
        .postcode("5555")
        .policyAgree(true)
        .build();
        Set<ConstraintViolation<UserRegistrationBinding>> registerBinding = validator.validate(userRegisterDTO);
        assertEquals(2,registerBinding.size());
    }

    @Test
    void updateProfile(){
        UserUpdateProfileBinding updateProfileDTO= UserUpdateProfileBinding.builder()
        .username("test")
        .fullName("test testov")
        .phoneNumber("3424324")
        .build();
        Set<ConstraintViolation<UserUpdateProfileBinding>> updateProfileBinding = validator.validate(updateProfileDTO);
        System.out.println(updateProfileBinding);
        assertEquals(2,updateProfileBinding.size());
    }
}

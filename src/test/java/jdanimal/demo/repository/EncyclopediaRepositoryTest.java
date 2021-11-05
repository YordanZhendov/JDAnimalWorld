package jdanimal.demo.repository;

import jdanimal.demo.DemoApplication;
import jdanimal.demo.data.EncyclopediaAnimal;
import jdanimal.demo.data.enums.AnimalType;
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
class EncyclopediaRepositoryTest {

    @Autowired
    private EncyclopediaRepository encyclopediaRepository;

    EncyclopediaAnimal savedEA;

    @BeforeEach
    void setUp() {
        EncyclopediaAnimal animal=new EncyclopediaAnimal();
        animal.setTypeOfAnimal(AnimalType.Dog);
        animal.setAnimalPhotoUrl("https://upload.wikimedia.org/wikipedia/commons/8/82/Golden_Retriever_standing_Tucker.jpg");
        animal.setDescription("The Golden Retriever is a medium-large gun dog that was bred to retrieve shot waterfowl, such as ducks and upland game birds, during hunting and shooting parties.[3] The name \"retriever\" refers to the breed's ability to retrieve shot game undamaged due to their soft mouth. Golden retrievers have an instinctive love of water, and are easy to train to basic or advanced obedience standards. They are a long-coated breed, with a dense inner coat that provides them with adequate warmth in the outdoors, and an outer coat that lies flat against their bodies and repels water. Golden retrievers are well suited to residency in suburban or country environments.[4] They shed copiously, particularly at the change of seasons, and require fairly regular grooming. The Golden Retriever was originally bred in Scotland in the mid-19th century.");
        savedEA = encyclopediaRepository.save(animal);
    }

    //Testing Encyclopedia Repository
    @Test
    void findAll() {
        assertEquals(1,encyclopediaRepository.count());
    }

    @Test
    void getAllByTypeOfAnimal() {
        assertNotNull(encyclopediaRepository.getAllByTypeOfAnimal(savedEA.getTypeOfAnimal()));
    }

    @AfterEach
    void tearDown() {
        encyclopediaRepository.deleteAll();
    }
}
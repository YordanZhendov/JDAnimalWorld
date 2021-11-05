package jdanimal.demo.repository;

import jdanimal.demo.DemoApplication;
import jdanimal.demo.data.Role;
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
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    Role rolaA;
    Role roleG;
    Role roleAn;

    @BeforeEach
    void setUp() {
        Role admin = new Role();
        admin.setAuthority("ADMIN");
        Role guest = new Role();
        guest.setAuthority("GUEST");
        Role anonymous = new Role();
        anonymous.setAuthority("ANONYMOUS");
        rolaA = roleRepository.save(admin);
        roleG = roleRepository.save(guest);
        roleAn = roleRepository.save(anonymous);
    }

    @Test
    void findByAuthority() {
        Role roleAdmin = roleRepository.findByAuthority("ADMIN");
        assertEquals(rolaA.getId(),roleAdmin.getId());
    }

    @AfterEach
    void tearDown() {
        roleRepository.deleteAll();
    }
}
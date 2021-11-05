package jdanimal.demo.service;

import jdanimal.demo.DemoApplication;
import jdanimal.demo.data.Role;
import jdanimal.demo.repository.RoleRepository;
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
class RoleServiceTest {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        roleService.seedRoles();
    }

    @Test
    void seedRoles() {
        assertEquals(3,roleRepository.count());
    }

    @Test
    void findAllRoles() {
        assertEquals(3,roleService.findAllRoles().size());
    }

    @Test
    void findByAuthority() {
        assertNotNull(roleService.findByAuthority("ADMIN"));
    }

    @AfterEach
    void tearDown() {
        roleRepository.deleteAll();
    }
}
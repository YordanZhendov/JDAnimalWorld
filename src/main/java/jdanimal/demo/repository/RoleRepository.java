package jdanimal.demo.repository;

import jdanimal.demo.data.DTO.RoleServiceDTO;
import jdanimal.demo.data.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByAuthority(String role);
}

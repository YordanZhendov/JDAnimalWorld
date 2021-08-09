package jdanimal.demo.service;

import jdanimal.demo.data.DTO.RoleServiceDTO;
import java.util.Set;


public interface RoleService {
    void seedRoles();
    Set<RoleServiceDTO> findAllRoles();
    RoleServiceDTO findByAuthority(String role);

}

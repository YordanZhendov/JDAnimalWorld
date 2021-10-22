package jdanimal.demo.service;

import jdanimal.demo.service.views.RoleServiceViewModel;
import java.util.Set;


public interface RoleService {
    void seedRoles();
    Set<RoleServiceViewModel> findAllRoles();
    RoleServiceViewModel findByAuthority(String role);

}

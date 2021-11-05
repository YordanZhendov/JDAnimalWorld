package jdanimal.demo.service;

import jdanimal.demo.service.views.RoleServiceViewModel;
import java.util.Set;


public interface RoleService {
    boolean seedRoles();
    Set<RoleServiceViewModel> findAllRoles();
    RoleServiceViewModel findByAuthority(String role);

}

package jdanimal.demo.service.impl;

import jdanimal.demo.service.views.RoleServiceViewModel;
import jdanimal.demo.data.Role;
import jdanimal.demo.repository.RoleRepository;
import jdanimal.demo.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Getter
@Setter
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public boolean seedRoles() {
        try {
            Role role=new Role("ADMIN");
            Role roleUser=new Role("USER");
            Role roleGuest=new Role("GUEST");

            this.roleRepository.saveAndFlush(role);
            this.roleRepository.saveAndFlush(roleUser);
            this.roleRepository.saveAndFlush(roleGuest);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Set<RoleServiceViewModel> findAllRoles() {
        return  this.roleRepository.findAll()
                .stream()
                .map(r->this.modelMapper.map(r, RoleServiceViewModel.class))
                .collect(Collectors.toSet());

    }

    @Override
    public RoleServiceViewModel findByAuthority(String role) {
        return this.modelMapper.map(this.roleRepository.findByAuthority(role), RoleServiceViewModel.class);
    }
}

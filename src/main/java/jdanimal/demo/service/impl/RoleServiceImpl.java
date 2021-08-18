package jdanimal.demo.service.impl;

import jdanimal.demo.data.DTO.RoleServiceDTO;
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
    public void seedRoles() {
        Role role=new Role("ADMIN");
        Role roleUser=new Role("USER");
        Role roleGuest=new Role("GUEST");

        this.roleRepository.saveAndFlush(role);
        this.roleRepository.saveAndFlush(roleUser);
        this.roleRepository.saveAndFlush(roleGuest);
    }

    @Override
    public Set<RoleServiceDTO> findAllRoles() {
        return  this.roleRepository.findAll()
                .stream()
                .map(r->this.modelMapper.map(r,RoleServiceDTO.class))
                .collect(Collectors.toSet());

    }

    @Override
    public RoleServiceDTO findByAuthority(String role) {
        return this.modelMapper.map(this.roleRepository.findByAuthority(role),RoleServiceDTO.class);
    }
}

package fr.gaetanquenouille.parcours.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.gaetanquenouille.parcours.DTO.RoleDTO;
import fr.gaetanquenouille.parcours.error.ResourceAlreadyExistingException;
import fr.gaetanquenouille.parcours.mapper.RoleMapper;
import fr.gaetanquenouille.parcours.repository.RoleRepository;

@Service
public class RoleService {
    
    @Autowired
    private RoleRepository roleRepository;

    // Create new role
    public RoleDTO createRole(RoleDTO roleDTO) {

        // Check if the role is well named
        if (!roleDTO.getName().startsWith("ROLE_")) {
            roleDTO.setName("ROLE_" + roleDTO.getName());
        }

        // Ensure that the role is capitalized
        roleDTO.setName(roleDTO.getName().toUpperCase());

        // Check if the role already exists
        if (roleRepository.findByName(roleDTO.getName()) != null) {
            throw new ResourceAlreadyExistingException("Role");
        }


        return RoleMapper.INSTANCE.toDTO(roleRepository.save(RoleMapper.INSTANCE.toEntity(roleDTO)));
    }
}

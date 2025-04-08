package fr.gaetanquenouille.parcours.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.gaetanquenouille.parcours.DTO.RoleDTO;
import fr.gaetanquenouille.parcours.DTO.SessionDTO;
import fr.gaetanquenouille.parcours.DTO.UserDTO;
import fr.gaetanquenouille.parcours.error.ResourceNotFoundException;
import fr.gaetanquenouille.parcours.mapper.UserMapper;
import fr.gaetanquenouille.parcours.model.Role;
import fr.gaetanquenouille.parcours.model.Session;
import fr.gaetanquenouille.parcours.repository.RoleRepository;
import fr.gaetanquenouille.parcours.repository.SessionRepository;
import fr.gaetanquenouille.parcours.repository.UserRepository;

@Service
public class UserService {

    private final SessionRepository sessionRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserMapper userMapper;

    UserService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    // Get all users
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
            .map(userMapper::toDTO)
            .collect(Collectors.toList());
    }

    // Get one user by id
    public UserDTO findUserById(Long id) {
        return userRepository.findById(id)
            .map(userMapper::toDTO)
            .orElseThrow(() -> new ResourceNotFoundException("User"));
    }

    // Edit user
    public void updateUser(Long id, UserDTO updatedUserDTO) {
        userRepository.findById(id)
            .map(user -> {
                if (updatedUserDTO.getFirst_name() != null) {
                    user.setFirst_name(updatedUserDTO.getFirst_name());
                }

                if (updatedUserDTO.getLast_name() != null) {
                    user.setLast_name(updatedUserDTO.getLast_name());
                }

                // update roles
                List<Role> updatedRoles = new ArrayList<>();
                for (RoleDTO roleDTO : updatedUserDTO.getRoles()) {
                    if (roleRepository.findById(roleDTO.getId()) != null) {
                        Role role = roleRepository.findById(roleDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("Role"));
                        updatedRoles.add(role);
                    }
                }
                user.setRoles(updatedRoles);

                // update sessions
                List<Session> updatedSessions = new ArrayList<Session>();
                for (SessionDTO sessionDTO : updatedUserDTO.getSessions()) {
                    if (sessionRepository.findById(sessionDTO.getId()) != null) {
                        Session session = sessionRepository.findById(sessionDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("Session"));
                        updatedSessions.add(session);
                    }
                }
                user.setSessions(updatedSessions);

                user.setUsername((updatedUserDTO.getFirst_name() + updatedUserDTO.getLast_name()).toLowerCase());
                
                return userRepository.save(user);
            })
            .orElseThrow(() -> new ResourceNotFoundException("User"));
    }

    // Delete user
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User");
        }
        userRepository.deleteById(id);
    }
}

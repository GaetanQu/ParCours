package fr.gaetanquenouille.parcours.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.gaetanquenouille.parcours.DTO.UserDTO;
import fr.gaetanquenouille.parcours.error.ResourceNotFoundException;
import fr.gaetanquenouille.parcours.mapper.UserMapper;
import fr.gaetanquenouille.parcours.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    // Get all users
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
            .map(UserMapper.INSTANCE::toDTO)
            .collect(Collectors.toList());
    }

    // Get one user by id
    public UserDTO findUserById(Long id) {
        return userRepository.findById(id)
            .map(UserMapper.INSTANCE::toDTO)
            .orElseThrow(()-> new ResourceNotFoundException("User"));
    }

    // Edit user
    public void updateUser(Long id, UserDTO userDTO) {
        userRepository.findById(id)
            .map(user -> {
            user.setFirst_name(userDTO.getFirst_name());
            user.setLast_name(userDTO.getLast_name());
            user.setRoles(userDTO.getRoles());
            user.setSessions(userDTO.getSessions());
            user.setUsername((userDTO.getFirst_name() + userDTO.getLast_name()).toLowerCase());
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

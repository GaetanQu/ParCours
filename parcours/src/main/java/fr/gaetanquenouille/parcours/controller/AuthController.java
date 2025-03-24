package fr.gaetanquenouille.parcours.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.gaetanquenouille.parcours.repository.UserRepository;
import lombok.AllArgsConstructor;
import fr.gaetanquenouille.parcours.DTO.LoginRequestDTO;
import fr.gaetanquenouille.parcours.DTO.RegisterRequestDTO;
import fr.gaetanquenouille.parcours.DTO.UserDTO;
import fr.gaetanquenouille.parcours.config.JwtUtils;
import fr.gaetanquenouille.parcours.mapper.UserMapper;
import fr.gaetanquenouille.parcours.model.User;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final JwtUtils jwtUtils;
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody RegisterRequestDTO registerRequestDTO) {
        // Check if the username already exists & add a suffix if it does
        int i = 1;
        String username = registerRequestDTO.getFirst_name() + registerRequestDTO.getLast_name();
        String checkedUsername = username;
        while(userRepository.findByUsername(checkedUsername) != null) {
            checkedUsername = username + Integer.toString(i);
            i++;
        }
        username = checkedUsername.toLowerCase();

        // Create a new user
        User user = new User();
        user.setFirst_name(registerRequestDTO.getFirst_name());
        user.setLast_name(registerRequestDTO.getLast_name());
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        userRepository.save(user);

        UserDTO userDTO = userMapper.toDTO(user);

        return ResponseEntity.ok().body(Map.of(
            "user", userDTO
        ));
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        System.out.println("Login endpoint hit with POST method");
        try {
            //Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequestDTO.getUsername().toLowerCase(),
                    loginRequestDTO.getPassword()
                )
            );

            // Get the user
            User user = userRepository.findByUsername(loginRequestDTO.getUsername().toLowerCase());
            UserDTO userDTO = userMapper.toDTO(user);

            // Generate a token
            String token = jwtUtils.generateToken(authentication.getName());



            return ResponseEntity.ok().body(Map.of(
                "token", token,
                "user", userDTO
            ));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(Map.of(
                "error", "Invalid credentials"
            ));
        }
    }
    
    // Get user from token
    @PostMapping("/user")
    public ResponseEntity<UserDTO> getUserFromToken(@RequestBody Map<String, String> request) {
        // Extract the token from the JSON request
        String token = request.get("token");
        // Get the username from the token
        String username = jwtUtils.getUsernameFromToken(token);

        // Get the user from the username
        User user = userRepository.findByUsername(username);
        UserDTO userDTO = userMapper.toDTO(user);

        // Return the user
        return ResponseEntity.ok().body(userDTO);
    }

}

package fr.gaetanquenouille.parcours.controller;

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
    public ResponseEntity<String> register (@RequestBody RegisterRequestDTO registerRequestDTO) {
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

        return ResponseEntity.ok("User registered successfully : " + userDTO.toString());
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        System.out.println("Login endpoint hit with POST method");
        try {
            //Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequestDTO.getUsername().toLowerCase(),
                    loginRequestDTO.getPassword()
                )
            );
            return ResponseEntity.ok(jwtUtils.generateToken(authentication.getName()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
    
    
}

package fr.gaetanquenouille.parcours.fixture;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import fr.gaetanquenouille.parcours.repository.RoleRepository;
import fr.gaetanquenouille.parcours.repository.SessionRepository;
import fr.gaetanquenouille.parcours.repository.SubjectRepository;
import fr.gaetanquenouille.parcours.repository.UserRepository;
import fr.gaetanquenouille.parcours.model.Role;
import fr.gaetanquenouille.parcours.model.Session;
import fr.gaetanquenouille.parcours.model.Subject;
import fr.gaetanquenouille.parcours.model.User;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final SessionRepository sessionRepository;

    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        // Load roles
        if (roleRepository.count() == 0) {
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);

            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);

            Role studentRole = new Role();
            studentRole.setName("ROLE_STUDENT");
            roleRepository.save(studentRole);

            Role parentRole = new Role();
            parentRole.setName("ROLE_PARENT");
            roleRepository.save(parentRole);

            Role teacherRole = new Role();
            teacherRole.setName("ROLE_TEACHER");
            roleRepository.save(teacherRole);

            System.out.println("Roles loaded into the database!");
        } else {
            System.out.println("Roles already exist. Skipping fixture loading.");
        }

        // Load subjects
        if (subjectRepository.count() == 0) { 
            for (int i = 0; i < 3; i++) {
                Subject subject = new Subject();
                subject.setLabel("Subject " + i);
                subjectRepository.save(subject);
            }
            System.out.println("Subjects loaded into the database!");
        } else {
            System.out.println("Subjects already exist. Skipping fixture loading.");
        }

        // Load an admin user
        if (userRepository.findByUsername("admin") == null) {
            User user = new User();
            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            
            user.setFirst_name("admin");
            user.setLast_name("");
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setRoles(Set.of(adminRole));
            userRepository.save(user);

            System.out.println("User loaded into the database!");
        }
        else {
            System.out.println("admin already exists");
        }

        // Load a user
        if (userRepository.findByUsername("johndoe") == null) {
            User user = new User();
            Role userRole = roleRepository.findByName("ROLE_USER");
            Role studentRole = roleRepository.findByName("ROLE_STUDENT");
            
            user.setFirst_name("john");
            user.setLast_name("doe");
            user.setUsername("johndoe");
            user.setPassword(passwordEncoder.encode("123"));
            user.setRoles(Set.of(userRole, studentRole));
            userRepository.save(user);

            System.out.println("User loaded into the database!");
        }
        else {
            System.out.println("user already exists");
        }

        // Load a teacher
        if (userRepository.findByUsername("teacher") == null) {
            User user = new User();
            Role teacherRole = roleRepository.findByName("ROLE_TEACHER");
            
            user.setFirst_name("teacher");
            user.setLast_name("");
            user.setUsername("teacher");
            user.setPassword(passwordEncoder.encode("teacher"));
            user.setRoles(Set.of(teacherRole));
            userRepository.save(user);

            System.out.println("User loaded into the database!");
        }
        else {
            System.out.println("teacher already exists");
        }

        // Load three session
        if (sessionRepository.count() == 0) {
            for (int i = 0; i < 3; i++) {
                Session session = new Session();
            session.setLabel("Session " + i);
            session.setBeginsAt(LocalDateTime.of(2025, 03, 23, 14, 00));
            session.setEndsAt(LocalDateTime.of(2025, 03, 23, 15, 00));
            sessionRepository.save(session);
            System.out.println("Session loaded into the database!");
            }
        } else {
            System.out.println("Session already exists. Skipping fixture loading.");
        }
    }
}

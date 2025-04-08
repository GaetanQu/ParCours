package fr.gaetanquenouille.parcours.fixture;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import fr.gaetanquenouille.parcours.repository.HomeworkRepository;
import fr.gaetanquenouille.parcours.repository.RoleRepository;
import fr.gaetanquenouille.parcours.repository.SessionRepository;
import fr.gaetanquenouille.parcours.repository.SubjectRepository;
import fr.gaetanquenouille.parcours.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import fr.gaetanquenouille.parcours.model.Homework;
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
    private final HomeworkRepository homeworkRepository;

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
        }

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        Role userRole = roleRepository.findByName("ROLE_USER");
        Role teacherRole = roleRepository.findByName("ROLE_TEACHER");
        Role studentRole = roleRepository.findByName("ROLE_STUDENT");

        // Load an admin user
        if (userRepository.findByUsername("admin") == null) {
            User user = new User();
            user.setFirst_name("admin");
            user.setLast_name("");
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setRoles(List.of(adminRole));
            userRepository.save(user);

            System.out.println("Admin loaded into the database!");
        }
        else {
            System.out.println("admin already exists");
        }

        // Load subjects
        if (subjectRepository.count() == 0) { 
            for (int i = 0; i < 3; i++) {
                Subject subject = new Subject();
                subject.setLabel("Subject " + i);
                subjectRepository.save(subject);

                System.out.println("Subject " + i + " loaded into the database!");
            }
        } else {
            System.out.println("Subjects already exist. Skipping fixture loading.");
        }

        // Load a session
        if (sessionRepository.count() == 0) {
            Session session = new Session();
            session.setLabel("Session ");
            session.setBeginsAt(LocalDateTime.of(2025, 03, 23, 14, 00));
            session.setEndsAt(LocalDateTime.of(2025, 03, 23, 15, 00));
            sessionRepository.save(session);
            System.out.println("Session loaded into the database!");
        } else {
            System.out.println("Session already exists. Skipping fixture loading.");
        }

        // Load a homework to the session
        if (homeworkRepository.count() == 0) {
            Homework homework = new Homework();
            homework.setLabel("Résoudre les équations");
            homework.setDescription("Résouds les équations suivantes : ");
            homework.setFileUrls(List.of("http://example.com/homework/math_homework.pdf"));
            homework.setSubject(subjectRepository.findAll().get(0));
            homework.setSession(sessionRepository.findAll().get(0));

            homeworkRepository.save(homework);
            System.out.println("Homework loaded into the database!");
        } else {
            System.out.println("Homework already exists. Skipping fixture loading.");
        }

        // Create an homework for no one into the session
            Homework freeHomework = new Homework();
            freeHomework.setLabel("Homework for no one");
            freeHomework.setDescription("This homework is for no one");
            freeHomework.setFileUrls(List.of("http://example.com/homework/math_homework.pdf"));
            freeHomework.setSession(sessionRepository.findAll().get(0));
            freeHomework.setSubject(subjectRepository.findAll().get(1));
            homeworkRepository.save(freeHomework);
            System.out.println("Homework for no one loaded into the database!");
        

        // Load a teacher
        if (userRepository.findByUsername("teacher") == null) {
            User user = new User();            
            user.setFirst_name("teacher");
            user.setLast_name("");
            user.setUsername("teacher");
            user.setPassword(passwordEncoder.encode("teacher"));
            user.setRoles(List.of(userRole, teacherRole));
            userRepository.save(user);

            System.out.println("Teacher loaded into the database!");
        }
        else {
            System.out.println("teacher already exists");
        }

        // Load a student
        if (userRepository.findByUsername("student") == null) {
            User student = new User();
            student.setFirst_name("student");
            student.setLast_name("");
            student.setUsername("student");
            student.setPassword(passwordEncoder.encode("student"));
            student.setRoles(List.of(userRole, studentRole));

            userRepository.save(student);

            System.out.println("Student loaded into the database !");
        }

        // Load a Subject to the session
        if (!subjectRepository.findAll().isEmpty() && !sessionRepository.findAll().isEmpty()) {
            Subject subject = subjectRepository.findAll().get(0);
            Session session = sessionRepository.findAll().get(0);

            session.setSubjects(List.of(subject));
            sessionRepository.save(session);

            System.out.println("Subject added to session");
        }

        // Load two users to the session
        if (!sessionRepository.findAll().isEmpty() && !userRepository.findAll().isEmpty()) {
            Session session = sessionRepository.findAll().get(0);
            User teacher = userRepository.findAll().get(1);
            User student = userRepository.findAll().get(2);

            session.setUsers(List.of(teacher, student));

            sessionRepository.save(session);
            System.out.println("Users added to the session");
        }
        else {
            System.out.println("Teacher can't be added to the session");
        }

        // Load homework to user
        if (userRepository.findById(3L).isPresent()) {
            User user = userRepository.findById(3L).orElseThrow(() -> new EntityNotFoundException("User not found"));
            Homework homework = homeworkRepository.findAll().get(0);

            user.setHomeworks(List.of(homework));

            userRepository.save(user);
        }
    }
}

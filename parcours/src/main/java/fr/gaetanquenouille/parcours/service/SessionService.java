package fr.gaetanquenouille.parcours.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import fr.gaetanquenouille.parcours.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.gaetanquenouille.parcours.DTO.HomeworkDTO;
import fr.gaetanquenouille.parcours.DTO.SessionDTO;
import fr.gaetanquenouille.parcours.DTO.SubjectDTO;
import fr.gaetanquenouille.parcours.DTO.UserDTO;
import fr.gaetanquenouille.parcours.error.ResourceNotFoundException;
import fr.gaetanquenouille.parcours.mapper.SessionMapper;
import fr.gaetanquenouille.parcours.model.Homework;
import fr.gaetanquenouille.parcours.model.Session;
import fr.gaetanquenouille.parcours.model.Subject;
import fr.gaetanquenouille.parcours.model.User;
import fr.gaetanquenouille.parcours.repository.HomeworkRepository;
import fr.gaetanquenouille.parcours.repository.SessionRepository;
import fr.gaetanquenouille.parcours.repository.SubjectRepository;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HomeworkRepository homeworkRepository;

    @Autowired
    private SessionMapper sessionMapper;

    // Get all sessions
    public List<SessionDTO> getAllSessions() {
        return sessionRepository.findAll().stream()
            .map(sessionMapper::toDTO)
            .collect(Collectors.toList());
    }

    // Get one session by id
    public SessionDTO findSessionById(Long id) {
        return sessionRepository.findById(id)
            .map(sessionMapper::toDTO)
            .orElseThrow(() -> new ResourceNotFoundException("Session"));
    }

    // Create a new session
    public Session createSession(SessionDTO sessionDTO) {
        Session session = new Session();
        session.setLabel(sessionDTO.getLabel());
        session.setBeginsAt(sessionDTO.getBeginsAt());
        session.setEndsAt(sessionDTO.getEndsAt());

        if (sessionDTO.getFileUrls() != null) {
            session.setFileUrls(sessionDTO.getFileUrls());            
        }

        if (sessionDTO.getUsers() != null) {
            List<User> users = new ArrayList<User>();
            for (UserDTO userDTO : sessionDTO.getUsers()) {
                User user = userRepository.findById(userDTO.getId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userDTO.getId()));
                users.add(user);
            }
            session.setUsers(users);   
        }

        List<Subject> subjects = new ArrayList<Subject>();
        for (SubjectDTO subjectDTO : sessionDTO.getSubjects()) {
            Subject subject = subjectRepository.findById(subjectDTO.getId()).orElseThrow(() -> new EntityNotFoundException("Subject not found with id: " + subjectDTO.getId()));
            subjects.add(subject);
        }
        session.setSubjects(subjects);

        if (sessionDTO.getHomeworks() != null) {
            List<Homework> homeworks = new ArrayList<Homework>();
            for (HomeworkDTO homeworkDTO : sessionDTO.getHomeworks()) {
                Homework homework = homeworkRepository.findById(homeworkDTO.getId()).orElseThrow(() -> new EntityNotFoundException("Homework not found with the id: " + homeworkDTO.getId()));
                homeworks.add(homework);
            }
            session.setHomeworks(homeworks);   
        }

        return sessionRepository.save(session);
    }

    // Update a session
    

    // Delete a session
    public void delete(Long id) {
        System.out.println(sessionRepository.findById(id));
        if (sessionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Session");
        }
        sessionRepository.deleteById(id);
    }

    // Get all the session of a user
    public List<SessionDTO> getAllSessionsOfUser(UserDTO userDTO) {
        System.out.println(userDTO.getId());
        return sessionRepository.findAllByUserId(userDTO.getId())
            .stream()
            .map(sessionMapper::toDTO)
            .collect(Collectors.toList());
    }
}
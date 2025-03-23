package fr.gaetanquenouille.parcours.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.gaetanquenouille.parcours.DTO.SessionDTO;
import fr.gaetanquenouille.parcours.error.ResourceNotFoundException;
import fr.gaetanquenouille.parcours.mapper.SessionMapper;
import fr.gaetanquenouille.parcours.model.Session;
import fr.gaetanquenouille.parcours.repository.SessionRepository;
import fr.gaetanquenouille.parcours.repository.SubjectRepository;
import fr.gaetanquenouille.parcours.repository.UserRepository;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserRepository userRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    // Get all sessions
    public List<SessionDTO> getAllSessions() {
        return sessionRepository.findAll().stream()
            .map(SessionMapper.INSTANCE::toDTO)
            .collect(Collectors.toList());
    }

    // Get one session by id
    public SessionDTO findSessionById(Long id) {
        return sessionRepository.findById(id)
            .map(SessionMapper.INSTANCE::toDTO)
            .orElseThrow(() -> new ResourceNotFoundException("Session"));
    }

    // Create a new session
    public Session createSession(SessionDTO sessionDTO) {
        Session session = SessionMapper.INSTANCE.toEntity(sessionDTO);
        return sessionRepository.save(session);
    }

    // Update a session
    public SessionDTO updateSession(Long id, SessionDTO sessionDTO) {
        Session session = sessionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Session"));

        session.setLabel(sessionDTO.getLabel());
        session.setBeginsAt(sessionDTO.getBeginsAt());
        session.setEndsAt(sessionDTO.getEndsAt());

        if (sessionDTO.getUsers() != null) {
            session.setUsers(
                sessionDTO.getUsers().stream()
                    .map(userDTO -> userRepository.findById(userDTO.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("User")))
                    .collect(Collectors.toSet())
            );
        }

        if (sessionDTO.getSubjects() != null) {
            session.setSubjects(
                sessionDTO.getSubjects().stream()
                    .map(subjectDTO -> subjectRepository.findById(subjectDTO.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Subject")))
                    .collect(Collectors.toSet())
            );
        }

        return SessionMapper.INSTANCE.toDTO(sessionRepository.save(session));
    }

    // Delete a session
    public void delete(Long id) {
        System.out.println(sessionRepository.findById(id));
        if (sessionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Session");
        }
        sessionRepository.deleteById(id);
    }
}
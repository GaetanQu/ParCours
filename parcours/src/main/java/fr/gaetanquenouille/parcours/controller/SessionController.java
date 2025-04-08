package fr.gaetanquenouille.parcours.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.gaetanquenouille.parcours.DTO.SessionDTO;
import fr.gaetanquenouille.parcours.DTO.UserDTO;
import fr.gaetanquenouille.parcours.model.Session;
import fr.gaetanquenouille.parcours.service.SessionService;


@RestController
@RequestMapping("/api/sessions")
public class SessionController {
    
    @Autowired
    private SessionService sessionService;

    // Get all sessions
    @GetMapping
    public List<SessionDTO> getAllSessions() {
        return sessionService.getAllSessions();
    }

    // Create a new session
    @PostMapping
    public Session createSessionEntity(@RequestBody SessionDTO sessionDTO) {
        return sessionService.createSession(sessionDTO);
    }
    
    // Get one session by id
    @GetMapping("/{id}")
    public SessionDTO getSessionById(@PathVariable Long id) {
        return sessionService.findSessionById(id);
    }
    
    /*
    // Update session
    @PutMapping("/{id}")
    public SessionDTO updateSession(@PathVariable Long id, @RequestBody SessionDTO sessionDTO) {
            return sessionService.updateSession(id, sessionDTO);
    }
    */

    // Delete session
    @DeleteMapping("/{id}")
    public String deleteSession(@PathVariable Long id) {
        sessionService.delete(id);
        return "Session with id has been deleted";
    }

    //Get all sessions from a user
    @PostMapping("/ofuser")
    public List<SessionDTO> getAllSessionsOfUser(@RequestBody UserDTO userDTO) {
        return sessionService.getAllSessionsOfUser(userDTO);
    }
}

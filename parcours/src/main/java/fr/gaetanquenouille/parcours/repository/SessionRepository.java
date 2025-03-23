package fr.gaetanquenouille.parcours.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.gaetanquenouille.parcours.model.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {
    
}
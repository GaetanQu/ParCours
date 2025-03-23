package fr.gaetanquenouille.parcours.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.gaetanquenouille.parcours.model.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    boolean findByLabel(String label);
}

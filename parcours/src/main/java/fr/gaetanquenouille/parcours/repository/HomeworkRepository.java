package fr.gaetanquenouille.parcours.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.gaetanquenouille.parcours.model.Homework;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, Long>{
    Homework findByLabel(String label);
}

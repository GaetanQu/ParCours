package fr.gaetanquenouille.parcours.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.gaetanquenouille.parcours.model.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    @Query(
        "SELECT s FROM Session s JOIN s.users u WHERE u.id = :userId"
    )
    List<Session> findAllByUserId(@Param("userId") Long userId);
}
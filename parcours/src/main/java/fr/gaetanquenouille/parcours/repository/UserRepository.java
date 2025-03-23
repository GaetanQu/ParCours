package fr.gaetanquenouille.parcours.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.gaetanquenouille.parcours.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);
}

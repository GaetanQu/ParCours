package fr.gaetanquenouille.parcours.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.gaetanquenouille.parcours.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}

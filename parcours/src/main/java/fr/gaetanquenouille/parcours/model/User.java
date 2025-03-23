package fr.gaetanquenouille.parcours.model;

import java.util.Set;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String first_name;

    @Column(nullable = false)
    private String last_name;

    @Column(nullable = false, unique = true)
    private String username = first_name + last_name;

    @Column(nullable = false)
    private String password;

    @ManyToMany(
        fetch = FetchType.EAGER,
        cascade = {CascadeType.REFRESH, CascadeType.MERGE}
    )
    @JoinTable(
        name = "user_role",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @ManyToMany(
        mappedBy = "users",
        fetch = FetchType.LAZY,
        cascade = {CascadeType.MERGE, CascadeType.REFRESH}
    )
    private Set<Session> sessions;
}

package fr.gaetanquenouille.parcours.model;

import java.time.LocalDateTime;
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
public class Session {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String label;

    @Column(nullable = false)
    private LocalDateTime beginsAt;

    @Column(nullable = false)
    private LocalDateTime endsAt;

    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = {CascadeType.MERGE, CascadeType.REFRESH}
    )
    @JoinTable(
        name = "session_user",
        joinColumns = @JoinColumn(name = "session_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users;

    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = {CascadeType.MERGE, CascadeType.REFRESH}
    )
    @JoinTable(
        name = "session_subject",
        joinColumns = @JoinColumn(name = "session_id"),
        inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjects;

}

package fr.gaetanquenouille.parcours.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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

    private List<String> fileUrls;

    @ManyToMany
    @Column(nullable = false)
    private List<User> users;

    @ManyToMany
    private List<Subject> subjects;

    @OneToMany(
        fetch = FetchType.EAGER,
        mappedBy = "session"
    )
    private List<Homework> homeworks;
}

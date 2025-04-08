package fr.gaetanquenouille.parcours.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Homework {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String label;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    private List<String> fileUrls;

    @ManyToMany(
        fetch = FetchType.EAGER,
        mappedBy = "homeworks"
    )
    private List<User> users;
    
    @ManyToOne
    private Subject subject;

    @ManyToOne(
        cascade = { CascadeType.MERGE },
        fetch = FetchType.LAZY
    )
    @JoinColumn(
        name = "session_id"
    )
    private Session session;

    @ManyToMany
    private List<User> hasDone;
}

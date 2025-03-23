package fr.gaetanquenouille.parcours.DTO;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionDTO {
    private Long id;
    private String label;
    private LocalDateTime beginsAt;
    private LocalDateTime endsAt;
    private Set<UserDTO> users;
    private Set<SubjectDTO> subjects;
}

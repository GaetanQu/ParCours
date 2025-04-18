package fr.gaetanquenouille.parcours.DTO;

import java.time.LocalDateTime;
import java.util.List;

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
    private List<String> fileUrls;
    private List<UserDTO> users;
    private List<SubjectDTO> subjects;
    private List<HomeworkDTO> homeworks;
}

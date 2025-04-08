package fr.gaetanquenouille.parcours.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeworkDTO {
    private Long id;
    private String label;
    private String description;
    private List<String> fileUrls;
    private List<UserDTO> users;
    private SubjectDTO subject;
    private SessionDTO session;
    private List<UserDTO> hasDone;
}

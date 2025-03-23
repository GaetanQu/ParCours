package fr.gaetanquenouille.parcours.DTO;

import java.util.Set;

import fr.gaetanquenouille.parcours.model.Role;
import fr.gaetanquenouille.parcours.model.Session;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String first_name;
    private String last_name;
    private String username;
    private Set<Role> roles;
    private Set<Session> sessions;
}

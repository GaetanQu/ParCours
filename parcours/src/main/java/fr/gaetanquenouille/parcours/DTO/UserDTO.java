package fr.gaetanquenouille.parcours.DTO;


import java.util.List;

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
    private List<RoleDTO> roles;
    private List<SessionDTO> sessions;
}

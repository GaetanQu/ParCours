package fr.gaetanquenouille.parcours.DTO;

import java.util.List;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String first_name;
    private String last_name;
    private List<RoleDTO> roles;
    private String password;
}

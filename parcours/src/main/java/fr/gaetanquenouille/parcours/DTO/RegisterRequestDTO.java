package fr.gaetanquenouille.parcours.DTO;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String first_name;
    private String last_name;
    private String password;
}

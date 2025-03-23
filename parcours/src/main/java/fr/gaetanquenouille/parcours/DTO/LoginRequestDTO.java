package fr.gaetanquenouille.parcours.DTO;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String username;
    private String password;
}

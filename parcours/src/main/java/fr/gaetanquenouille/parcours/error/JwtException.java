package fr.gaetanquenouille.parcours.error;

public class JwtException extends RuntimeException {

    public JwtException(){
        super("Invalid JWT token");
    }
}

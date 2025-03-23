package fr.gaetanquenouille.parcours.error;

public class ResourceAlreadyExistingException extends RuntimeException {

    public ResourceAlreadyExistingException(String resource) {
        super(resource + " already exists");
    }
    
}

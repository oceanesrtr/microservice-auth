package inf1013.backend.authservice.exception;

/**
 * Exception levée lorsqu'une ressource existe déjà,
 * par exemple un email déjà enregistré.
 */
public class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
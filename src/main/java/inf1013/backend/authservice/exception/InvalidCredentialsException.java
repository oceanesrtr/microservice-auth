package inf1013.backend.authservice.exception;

/**
 * Exception levée lorsque les identifiants de connexion sont incorrects.
 */
public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
package inf1013.backend.authservice.exception;

import inf1013.backend.authservice.service.implementation.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Gestionnaire global des exceptions.
 * Permet de retourner des réponses propres au frontend.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gère le cas où une ressource existe déjà.
     *
     * @param ex exception levée
     * @return réponse HTTP 409
     */
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleAlreadyExists(ResourceAlreadyExistsException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    /**
     * Gère le cas d'identifiants invalides.
     *
     * @param ex exception levée
     * @return réponse HTTP 401
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleInvalidCredentials(InvalidCredentialsException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    /**
     * Gère les erreurs de validation des DTO.
     *
     * @param ex exception levée
     * @return réponse HTTP 400
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("message", "Données invalides");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    /**
     * Gère toutes les autres erreurs non prévues.
     *
     * @param ex exception levée
     * @return réponse HTTP 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneric(Exception ex) {
        Map<String, String> body = new HashMap<>();
        body.put("message", "Erreur interne du serveur");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(ResourceNotFoundException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}
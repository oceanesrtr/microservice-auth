package inf1013.backend.authservice.dto;

import lombok.*;

/**
 * DTO retourné après login ou inscription
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDto {

    /**
     * Token JWT
     */
    private String token;

    /**
     * Utilisateur connecté
     */
    private UserResponseDto user;
}
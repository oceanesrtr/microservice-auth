package inf1013.backend.authservice.service.interfaces;

import inf1013.backend.authservice.dto.AuthResponseDto;
import inf1013.backend.authservice.dto.LoginRequestDto;
import inf1013.backend.authservice.dto.RegisterRequestDto;
import inf1013.backend.authservice.dto.UserResponseDto;
import inf1013.backend.authservice.dto.ValidateTokenResponseDto;
import inf1013.backend.authservice.dto.UpdateUserRequestDto;

/**
 * Interface du service d'authentification.
 * Elle définit les opérations disponibles.
 */
public interface AuthService {

    /**
     * Inscription d'un nouvel utilisateur.
     *
     * @param request données d'inscription
     * @return token + utilisateur connecté
     */
    AuthResponseDto register(RegisterRequestDto request);

    /**
     * Connexion d'un utilisateur existant.
     *
     * @param request données de connexion
     * @return token + utilisateur connecté
     */
    AuthResponseDto login(LoginRequestDto request);

    /**
     * Vérifie si un token JWT est valide.
     *
     * @param token token JWT
     * @return résultat de validation
     */
    ValidateTokenResponseDto validateToken(String token);
    /**
     * Met à jour le profil d'un utilisateur.
     *
     * @param id identifiant utilisateur
     * @param request nouvelles données du profil
     * @return utilisateur mis à jour
     */
    UserResponseDto updateProfile(Long id, UpdateUserRequestDto request);

    void resetPassword(String email, String newPassword);

}
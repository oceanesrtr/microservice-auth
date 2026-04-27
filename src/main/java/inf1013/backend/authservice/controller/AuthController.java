package inf1013.backend.authservice.controller;

import inf1013.backend.authservice.dto.AuthResponseDto;
import inf1013.backend.authservice.dto.LoginRequestDto;
import inf1013.backend.authservice.dto.RegisterRequestDto;
import inf1013.backend.authservice.dto.ValidateTokenResponseDto;
import inf1013.backend.authservice.service.implementation.ResourceNotFoundException;
import inf1013.backend.authservice.service.interfaces.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import inf1013.backend.authservice.dto.UpdateUserRequestDto;
import inf1013.backend.authservice.dto.UserResponseDto;

import java.util.Map;

/**
 * Contrôleur REST gérant les endpoints d'authentification.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = {"https://*.netlify.app", "http://localhost:*"})

public class AuthController {

    /**
     * Service métier d'authentification
     */
    private final AuthService authService;

    /**
     * Endpoint d'inscription.
     *
     * @param request données d'inscription
     * @return token + utilisateur créé
     */
    @PostMapping("/register")
    public AuthResponseDto register(@Valid @RequestBody RegisterRequestDto request) {
        return authService.register(request);
    }

    /**
     * Endpoint de connexion.
     *
     * @param request données de connexion
     * @return token + utilisateur connecté
     */
    @PostMapping("/login")
    public AuthResponseDto login(@Valid @RequestBody LoginRequestDto request) {
        return authService.login(request);
    }

    /**
     * Endpoint permettant de vérifier si un token JWT est valide.
     *
     * @param authorizationHeader header Authorization contenant "Bearer <token>"
     * @return résultat de validation du token
     */
    @GetMapping("/validate")
    public ValidateTokenResponseDto validateToken(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        // Retire le préfixe "Bearer " pour ne garder que le token
        String token = authorizationHeader.replace("Bearer ", "");

        return authService.validateToken(token);
    }

    /**
     * Endpoint pour modifier le profil utilisateur.
     *
     * @param id identifiant de l'utilisateur
     * @param request nouvelles informations
     * @return utilisateur mis à jour
     */
    @PutMapping("/users/{id}")
    public UserResponseDto updateProfile(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequestDto request
    ) {
        return authService.updateProfile(id, request);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, Object>> resetPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String newPassword = body.get("newPassword");

        authService.resetPassword(email, newPassword);

        return ResponseEntity.ok(Map.of("message", "Mot de passe réinitialisé avec succès."));
    }
}
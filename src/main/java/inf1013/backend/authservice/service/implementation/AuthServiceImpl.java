package inf1013.backend.authservice.service.implementation;

import inf1013.backend.authservice.dto.AuthResponseDto;
import inf1013.backend.authservice.dto.LoginRequestDto;
import inf1013.backend.authservice.dto.RegisterRequestDto;
import inf1013.backend.authservice.dto.UserResponseDto;
import inf1013.backend.authservice.dto.ValidateTokenResponseDto;
import inf1013.backend.authservice.exception.InvalidCredentialsException;
import inf1013.backend.authservice.exception.ResourceAlreadyExistsException;
import inf1013.backend.authservice.model.User;
import inf1013.backend.authservice.repository.UserRepository;
import inf1013.backend.authservice.security.JwtService;
import inf1013.backend.authservice.service.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import inf1013.backend.authservice.dto.UpdateUserRequestDto;
import inf1013.backend.authservice.dto.UserResponseDto;
import java.util.Optional;

/**
 * Implémentation du service d'authentification.
 * Contient la logique métier liée au login, à l'inscription et au JWT.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    /**
     * Repository pour accéder aux utilisateurs en base
     */
    private final UserRepository userRepository;

    /**
     * Encoder pour sécuriser les mots de passe
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Service de gestion des JWT
     */
    private final JwtService jwtService;

    /**
     * Inscrit un nouvel utilisateur.
     *
     * @param request données reçues du frontend
     * @return réponse contenant le token et l'utilisateur
     */
    @Override
    public AuthResponseDto register(RegisterRequestDto request) {

        // Vérifie si l'email existe déjà
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Ce courriel est déjà utilisé.");
        }

        // Création de l'utilisateur
        User user = User.builder()
                .prenom(request.getPrenom())
                .nom(request.getNom())
                .telephone(request.getTelephone())
                .email(request.getEmail())
                .adresse(request.getAdresse())
                // Le mot de passe est encodé avant sauvegarde
                .password(passwordEncoder.encode(request.getPassword()))
                // Rôle par défaut
                .role("USER")
                .build();

        // Sauvegarde en base de données
        User savedUser = userRepository.save(user);

        // Génération du token JWT
        String token = jwtService.generateToken(savedUser);

        // Retour au frontend
        return AuthResponseDto.builder()
                .token(token)
                .user(mapToUserResponse(savedUser))
                .build();
    }

    /**
     * Connecte un utilisateur.
     *
     * @param request email + mot de passe
     * @return réponse contenant le token et l'utilisateur
     */
    @Override
    public AuthResponseDto login(LoginRequestDto request) {

        // Recherche de l'utilisateur par email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Courriel ou mot de passe incorrect."));

        // Vérifie si le mot de passe fourni correspond au mot de passe encodé en base
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Courriel ou mot de passe incorrect.");
        }

        // Génère le JWT
        String token = jwtService.generateToken(user);

        return AuthResponseDto.builder()
                .token(token)
                .user(mapToUserResponse(user))
                .build();
    }

    /**
     * Vérifie la validité d'un token JWT.
     *
     * @param token token JWT
     * @return détails sur la validité du token
     */
    @Override
    public ValidateTokenResponseDto validateToken(String token) {
        boolean valid = jwtService.isTokenValid(token);

        // Si le token est invalide, on retourne false
        if (!valid) {
            return ValidateTokenResponseDto.builder()
                    .valid(false)
                    .userId(null)
                    .email(null)
                    .role(null)
                    .build();
        }

        // Si valide, on extrait les informations utiles
        return ValidateTokenResponseDto.builder()
                .valid(true)
                .userId(jwtService.extractUserId(token))
                .email(jwtService.extractEmail(token))
                .role(jwtService.extractRole(token))
                .build();
    }

    /**
     * Convertit une entité User en UserResponseDto.
     *
     * @param user entité utilisateur
     * @return DTO sans mot de passe
     */
    private UserResponseDto mapToUserResponse(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .prenom(user.getPrenom())
                .nom(user.getNom())
                .telephone(user.getTelephone())
                .email(user.getEmail())
                .adresse(user.getAdresse())
                .role(user.getRole())
                .build();
    }

    @Override
    public UserResponseDto updateProfile(Long id, UpdateUserRequestDto request) {

        // Recherche de l'utilisateur par son identifiant
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable."));

        // Vérifie si le nouvel email appartient déjà à un autre utilisateur
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent() && !existingUser.get().getId().equals(id)) {
            throw new ResourceAlreadyExistsException("Ce courriel est déjà utilisé.");
        }

        // Mise à jour des champs modifiables
        user.setPrenom(request.getPrenom());
        user.setNom(request.getNom());
        user.setTelephone(request.getTelephone());
        user.setEmail(request.getEmail());
        user.setAdresse(request.getAdresse());

        // Sauvegarde des nouvelles informations en base
        User updatedUser = userRepository.save(user);

        // Retour des données mises à jour
        return mapToUserResponse(updatedUser);

    }

    // AuthServiceImpl.java
    @Override
    public void resetPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Aucun compte associé à cet email."));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
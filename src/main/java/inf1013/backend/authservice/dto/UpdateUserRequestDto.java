package inf1013.backend.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO utilisé pour la mise à jour du profil utilisateur.
 */
@Data
public class UpdateUserRequestDto {

    /**
     * Prénom de l'utilisateur
     */
    @NotBlank
    private String prenom;

    /**
     * Nom de l'utilisateur
     */
    @NotBlank
    private String nom;

    /**
     * Téléphone de l'utilisateur
     */
    @NotBlank
    private String telephone;

    /**
     * Adresse email de l'utilisateur
     */
    @Email
    @NotBlank
    private String email;

    /**
     * Adresse de l'utilisateur
     */
    @NotBlank
    private String adresse;
}
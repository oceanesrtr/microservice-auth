package inf1013.backend.authservice.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * DTO pour l'inscription
 */
@Data
public class RegisterRequestDto {

    @NotBlank
    private String prenom;

    @NotBlank
    private String nom;

    @NotBlank
    private String telephone;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String adresse;

    @NotBlank
    @Size(min = 6)
    private String password;
}
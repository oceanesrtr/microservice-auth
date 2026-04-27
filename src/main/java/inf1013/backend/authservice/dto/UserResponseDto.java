package inf1013.backend.authservice.dto;

import lombok.*;

/**
 * DTO retourné au frontend (sans mot de passe)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {

    private Long id;
    private String prenom;
    private String nom;
    private String telephone;
    private String email;
    private String adresse;
    private String role;
}
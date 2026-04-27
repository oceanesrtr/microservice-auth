package inf1013.backend.authservice.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * DTO pour la connexion
 */
@Data
public class LoginRequestDto {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;
}
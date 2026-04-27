package inf1013.backend.authservice.dto;

import lombok.*;

/**
 * DTO pour valider un token
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidateTokenResponseDto {

    private boolean valid;
    private Long userId;
    private String email;
    private String role;
}
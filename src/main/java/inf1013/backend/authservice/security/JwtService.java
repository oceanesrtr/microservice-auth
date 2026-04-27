package inf1013.backend.authservice.security;

import inf1013.backend.authservice.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

/**
 * Service responsable de la création et de la lecture des tokens JWT.
 */
@Service
public class JwtService {

    /**
     * Clé secrète définie dans application.yaml
     */
    @Value("${app.jwt.secret}")
    private String secret;

    /**
     * Durée de validité du token en millisecondes
     */
    @Value("${app.jwt.expiration}")
    private long jwtExpiration;

    /**
     * Génère un token JWT à partir des informations d'un utilisateur.
     *
     * @param user utilisateur connecté
     * @return token JWT signé
     */
    public String generateToken(User user) {
        return Jwts.builder()
                // Sujet principal du token : email de l'utilisateur
                .subject(user.getEmail())
                // Informations supplémentaires stockées dans le token
                .claim("userId", user.getId())
                .claim("role", user.getRole())
                // Date de création du token
                .issuedAt(new Date())
                // Date d'expiration du token
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                // Signature du token avec la clé secrète
                .signWith(getSignInKey())
                .compact();
    }

    /**
     * Vérifie si un token est valide.
     *
     * @param token token JWT
     * @return true si valide, false sinon
     */
    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Extrait l'email contenu dans le token.
     *
     * @param token token JWT
     * @return email
     */
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Extrait l'identifiant utilisateur contenu dans le token.
     *
     * @param token token JWT
     * @return userId
     */
    public Long extractUserId(String token) {
        Object value = extractAllClaims(token).get("userId");

        // Gestion des différents types possibles
        if (value instanceof Integer integerValue) {
            return integerValue.longValue();
        }
        if (value instanceof Long longValue) {
            return longValue;
        }

        return Long.parseLong(value.toString());
    }

    /**
     * Extrait le rôle contenu dans le token.
     *
     * @param token token JWT
     * @return rôle utilisateur
     */
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    /**
     * Extrait l'ensemble des claims du token.
     *
     * @param token token JWT
     * @return claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Génère la clé de signature à partir du secret.
     *
     * @return clé secrète utilisable pour signer/vérifier les JWT
     */
    private SecretKey getSignInKey() {
        String encodedSecret = Base64.getEncoder().encodeToString(secret.getBytes());
        byte[] keyBytes = Decoders.BASE64.decode(encodedSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
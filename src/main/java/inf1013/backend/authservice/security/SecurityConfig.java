package inf1013.backend.authservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration de sécurité Spring Security.
 */
@Configuration
public class SecurityConfig {

    /**
     * Définit les règles de sécurité HTTP de l'application.
     *
     * @param http configuration HTTP Spring Security
     * @return chaîne de filtres de sécurité
     * @throws Exception en cas d'erreur de configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Désactivation de CSRF pour faciliter les appels API REST
                .csrf(csrf -> csrf.disable())

                // Active la configuration CORS définie dans CorsConfig
                .cors(Customizer.withDefaults())

                // Autorisations des routes
                .authorizeHttpRequests(auth -> auth
                        // Toutes les routes d'authentification sont accessibles sans connexion
                        .requestMatchers("/api/auth/**").permitAll()
                        // Toute autre route nécessitera une authentification
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    /**
     * Bean pour encoder les mots de passe avec BCrypt.
     *
     * @return encodeur de mot de passe
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
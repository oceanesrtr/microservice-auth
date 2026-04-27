package inf1013.backend.authservice.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entité représentant un utilisateur dans la base de données
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    /**
     * Identifiant unique (clé primaire)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Prénom
     */
    @Column(nullable = false)
    private String prenom;

    /**
     * Nom
     */
    @Column(nullable = false)
    private String nom;

    /**
     * Téléphone
     */
    @Column(nullable = false)
    private String telephone;

    /**
     * Email unique
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Adresse
     */
    @Column(nullable = false)
    private String adresse;

    /**
     * Mot de passe (encodé)
     */
    @Column(nullable = false)
    private String password;

    /**
     * Rôle (USER par défaut)
     */
    @Column(nullable = false)
    private String role;
}
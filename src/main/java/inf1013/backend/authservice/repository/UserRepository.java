package inf1013.backend.authservice.repository;

import inf1013.backend.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository pour accéder à la table users
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Trouver un utilisateur par email
     */
    Optional<User> findByEmail(String email);

    /**
     * Vérifier si un email existe déjà
     */
    boolean existsByEmail(String email);
}
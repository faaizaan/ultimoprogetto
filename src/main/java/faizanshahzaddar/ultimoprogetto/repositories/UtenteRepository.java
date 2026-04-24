package faizanshahzaddar.ultimoprogetto.repositories;

import faizanshahzaddar.ultimoprogetto.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UtenteRepository extends JpaRepository<Utente, UUID> {
    boolean existsByEmail(String email);
    Optional<Utente> findByEmail(String email);
}

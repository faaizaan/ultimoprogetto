package faizanshahzaddar.ultimoprogetto.repositories;

import faizanshahzaddar.ultimoprogetto.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, UUID> {
    List<Prenotazione> findByUtente_Id(UUID utenteId);
    Optional<Prenotazione> findByUtente_IdAndEvento_Id(UUID utenteId, UUID eventoId);
}

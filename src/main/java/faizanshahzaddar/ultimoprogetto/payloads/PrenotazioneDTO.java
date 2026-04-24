package faizanshahzaddar.ultimoprogetto.payloads;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record PrenotazioneDTO(
        @NotNull(message = "numero posti obbligatorio")
        int nPosti,
        @NotNull(message = "id del evento obbligatorio")
        UUID eventoId


) {
}

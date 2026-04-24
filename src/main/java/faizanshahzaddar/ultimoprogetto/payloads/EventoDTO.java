package faizanshahzaddar.ultimoprogetto.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record EventoDTO(
        @NotBlank(message = "il titolo è obbligatorio")
        String titolo,
        @NotBlank(message = "la descrizione è obbligatoria")
        String descrizione,
        @NotNull(message = "la data è obbligatoria")
        LocalDate data,
        @NotBlank(message = "il luogo è obbligatorio")
        String luogo,
        @NotNull(message = "i posti sono obbligatori")
        int nPostiDisponibili



) {
}

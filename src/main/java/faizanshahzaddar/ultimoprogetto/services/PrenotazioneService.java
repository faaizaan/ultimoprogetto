package faizanshahzaddar.ultimoprogetto.services;

import faizanshahzaddar.ultimoprogetto.entities.Evento;
import faizanshahzaddar.ultimoprogetto.entities.Prenotazione;
import faizanshahzaddar.ultimoprogetto.entities.Utente;
import faizanshahzaddar.ultimoprogetto.exceptions.BadRequestException;
import faizanshahzaddar.ultimoprogetto.exceptions.NotFoundException;
import faizanshahzaddar.ultimoprogetto.exceptions.UnauthorizedException;
import faizanshahzaddar.ultimoprogetto.payloads.PrenotazioneDTO;
import faizanshahzaddar.ultimoprogetto.repositories.EventoRepository;
import faizanshahzaddar.ultimoprogetto.repositories.PrenotazioneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;
    private final EventoRepository eventoRepository;

    public PrenotazioneService(PrenotazioneRepository prenotazioneRepository,EventoRepository eventoRepository) {
        this.prenotazioneRepository = prenotazioneRepository;
        this.eventoRepository = eventoRepository;
    }

    public Prenotazione save(PrenotazioneDTO body, Utente utente){
Evento evento = eventoRepository.findById(body.eventoId()).orElseThrow(() -> new NotFoundException("evento non trovato"));
        if (evento.getnPostiDisponibili() < body.nPosti())throw new BadRequestException("non ci sono posti disponibili");
    if (prenotazioneRepository.findByUtente_IdAndEvento_Id(utente.getId(), evento.getId()).isPresent())throw new BadRequestException("hai gia prenotato");
    evento.setnPostiDisponibili(evento.getnPostiDisponibili() - body.nPosti());
eventoRepository.save(evento);
Prenotazione newPrenotazione = new Prenotazione(body.nPosti(), evento, utente);

Prenotazione salvaPrenotazione = prenotazioneRepository.save(newPrenotazione);
        log.info("Prenotazione con id " + salvaPrenotazione.getId() + " salvata correttamente");

        return salvaPrenotazione;

    }
    public Prenotazione findById(UUID prenotazioneId) {
        return prenotazioneRepository.findById(prenotazioneId).orElseThrow(() -> new NotFoundException("Prenotazione non trovata"));
    }

}

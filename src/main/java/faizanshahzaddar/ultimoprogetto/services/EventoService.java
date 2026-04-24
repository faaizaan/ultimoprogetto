package faizanshahzaddar.ultimoprogetto.services;


import faizanshahzaddar.ultimoprogetto.entities.Evento;
import faizanshahzaddar.ultimoprogetto.entities.Role;
import faizanshahzaddar.ultimoprogetto.entities.Utente;
import faizanshahzaddar.ultimoprogetto.exceptions.BadRequestException;
import faizanshahzaddar.ultimoprogetto.exceptions.NotFoundException;
import faizanshahzaddar.ultimoprogetto.exceptions.UnauthorizedException;
import faizanshahzaddar.ultimoprogetto.payloads.EventoDTO;
import faizanshahzaddar.ultimoprogetto.repositories.EventoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class EventoService {


    private final EventoRepository eventoRepository;


    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }
    public Evento save(EventoDTO body, Utente organizzatore){
     if (organizzatore.getRole() != Role.ORGANIZZATOREDIEVENTI)throw new UnauthorizedException("solo gli organizzatori possono creare eventi");

     if (body.nPostiDisponibili() <= 0 )throw new BadRequestException("i posti disponibili devono essere piu di 0");

     Evento newEvento = new Evento(body.titolo(), body.descrizione(), body.data(), body.luogo(), body.nPostiDisponibili(), organizzatore);
        Evento savedEvento = this.eventoRepository.save(newEvento);

        log.info("L'evento con id " + savedEvento.getId() + " è stato salvato correttamente");

        return savedEvento;
    }

    public Evento findById(UUID eventoId){
        return this.eventoRepository.findById(eventoId).orElseThrow(()-> new NotFoundException(eventoId));
    }
    public Evento aggiornaEvento(UUID eventoId, EventoDTO body, Utente organizzatore){

        Evento found = this.findById(eventoId);

        if (!found.getOrganizzatore().getId().equals(organizzatore.getId()))throw new UnauthorizedException("puoi modificare solo i tuoi eventi");

        found.setTitolo(body.titolo());
        found.setDescrizione(body.descrizione());
        found.setData(body.data());
        found.setLuogo(body.luogo());
        found.setnPostiDisponibili(body.nPostiDisponibili());

        Evento updatedEvento = this.eventoRepository.save(found);

        log.info("L'evento con id " + updatedEvento.getId() + " è stato modificato correttamente");

        return updatedEvento;
    }

    public void eliminaEvento(UUID eventoId, Utente organizzatore) {

        Evento found = this.findById(eventoId);

        if (!found.getOrganizzatore().getId().equals(organizzatore.getId())) {
            throw new UnauthorizedException("Puoi eliminare solo i tuoi eventi");
        }

        this.eventoRepository.delete(found);

        log.info("L'evento con id " + eventoId + " è stato eliminato correttamente");
    }

    public List<Evento> findAll() {
        return this.eventoRepository.findAll();
    }

}

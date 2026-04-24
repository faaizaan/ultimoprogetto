package faizanshahzaddar.ultimoprogetto.controllers;

import faizanshahzaddar.ultimoprogetto.entities.Evento;
import faizanshahzaddar.ultimoprogetto.entities.Utente;
import faizanshahzaddar.ultimoprogetto.exceptions.ValidationException;
import faizanshahzaddar.ultimoprogetto.payloads.EventoDTO;
import faizanshahzaddar.ultimoprogetto.payloads.NewEventoRespDTO;
import faizanshahzaddar.ultimoprogetto.services.EventoService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/eventi")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }


    @GetMapping("/{eventoId}")
    public Evento getById(@PathVariable UUID eventoId) {return this.eventoService.findById(eventoId);}


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewEventoRespDTO saveEvento(@RequestBody @Validated EventoDTO body, BindingResult validationResult, @AuthenticationPrincipal Utente organizzatore){
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        }
        Evento newEvento = this.eventoService.save(body,organizzatore);
        return new NewEventoRespDTO(newEvento.getId());
    }

    @PutMapping("/{eventoId}")
    public Evento aggiornaEvento(@PathVariable UUID eventoId, @AuthenticationPrincipal Utente organizzatore, @RequestBody @Validated EventoDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        }
        return this.eventoService.aggiornaEvento(eventoId,body,organizzatore);
    }

    @DeleteMapping("/{eventoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvento(@PathVariable UUID eventoId, @AuthenticationPrincipal Utente organizzatore)
    {
        this.eventoService.eliminaEvento(eventoId, organizzatore);
    }
}

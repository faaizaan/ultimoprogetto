package faizanshahzaddar.ultimoprogetto.controllers;

import faizanshahzaddar.ultimoprogetto.entities.Prenotazione;
import faizanshahzaddar.ultimoprogetto.entities.Utente;
import faizanshahzaddar.ultimoprogetto.exceptions.ValidationException;
import faizanshahzaddar.ultimoprogetto.payloads.NewPrenotazioneRespDTO;
import faizanshahzaddar.ultimoprogetto.payloads.PrenotazioneDTO;
import faizanshahzaddar.ultimoprogetto.services.PrenotazioneService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;

    public PrenotazioneController(PrenotazioneService prenotazioneService) {
        this.prenotazioneService = prenotazioneService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewPrenotazioneRespDTO savePrenotazione(@AuthenticationPrincipal Utente utente, @RequestBody @Validated PrenotazioneDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        }

        Prenotazione newPrenotazione = this.prenotazioneService.save(body,utente);
        return new NewPrenotazioneRespDTO(newPrenotazione.getId());
    }



}

package faizanshahzaddar.ultimoprogetto.controllers;


import faizanshahzaddar.ultimoprogetto.entities.Utente;
import faizanshahzaddar.ultimoprogetto.exceptions.ValidationException;
import faizanshahzaddar.ultimoprogetto.payloads.LoginDTO;
import faizanshahzaddar.ultimoprogetto.payloads.LoginRespDTO;
import faizanshahzaddar.ultimoprogetto.payloads.NewUtenteRespDTO;
import faizanshahzaddar.ultimoprogetto.payloads.UtenteDTO;
import faizanshahzaddar.ultimoprogetto.services.AuthService;
import faizanshahzaddar.ultimoprogetto.services.UtenteService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UtenteService utenteService;

    public AuthController(AuthService authService, UtenteService utenteService) {
        this.authService = authService;
        this.utenteService = utenteService;
    }

    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody LoginDTO body) {
        return new LoginRespDTO(this.authService.checkCredentialsAndGenerateToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED) // 201
    public NewUtenteRespDTO saveUser(@RequestBody @Validated UtenteDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        }

        Utente newUtente = this.utenteService.salvaUtente(body);
        return new NewUtenteRespDTO(newUtente.getId());
    }

    @PostMapping("/register/organizzatore")
    @ResponseStatus(HttpStatus.CREATED)
    public NewUtenteRespDTO saveOrganizzatore(@RequestBody @Validated UtenteDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();throw new ValidationException(errors);
        }

        Utente newOrganizer = this.utenteService.salvaOrganizzatore(body);

        return new NewUtenteRespDTO(newOrganizer.getId());
    }
}

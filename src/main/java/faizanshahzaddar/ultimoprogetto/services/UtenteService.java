package faizanshahzaddar.ultimoprogetto.services;


import faizanshahzaddar.ultimoprogetto.entities.Role;
import faizanshahzaddar.ultimoprogetto.entities.Utente;
import faizanshahzaddar.ultimoprogetto.exceptions.BadRequestException;
import faizanshahzaddar.ultimoprogetto.exceptions.NotFoundException;
import faizanshahzaddar.ultimoprogetto.payloads.UtenteDTO;
import faizanshahzaddar.ultimoprogetto.repositories.UtenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    public UtenteService(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Utente salvaUtente(UtenteDTO body) {
        if (this.utenteRepository.existsByEmail(body.email()))
            throw new BadRequestException("L'indirizzo email " + body.email() + " è già in uso!");

        Utente newUser = new Utente(body.email(), this.passwordEncoder.encode(body.password()), body.nome(), body.cognome(), Role.UTENTENORMALE);
        Utente savedUser = this.utenteRepository.save(newUser);


        log.info("L'utente con id " + savedUser.getId() + " è stato salvato correttamente!");


        return savedUser;
    }

    public Utente salvaOrganizzatore(UtenteDTO body) {

        if (this.utenteRepository.existsByEmail(body.email()))
            throw new BadRequestException("L'indirizzo email " + body.email() + " è già in uso!");

        Utente newUser = new Utente(body.email(), this.passwordEncoder.encode(body.password()), body.nome(), body.cognome(), Role.ORGANIZZATOREDIEVENTI);
        Utente savedUser = this.utenteRepository.save(newUser);

        log.info("L'utente con id " + savedUser.getId() + " è stato salvato correttamente!");

        return savedUser;
    }
    public Utente findById(UUID utenteId){
        return this.utenteRepository.findById(utenteId).orElseThrow(()-> new NotFoundException(utenteId));
    }
    public Utente findByEmail(String email) {
        return this.utenteRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("L'utente con email " + email + " non è stato trovato!"));
    }




}

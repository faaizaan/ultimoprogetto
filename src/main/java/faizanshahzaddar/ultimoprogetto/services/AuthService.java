package faizanshahzaddar.ultimoprogetto.services;

import faizanshahzaddar.ultimoprogetto.entities.Utente;
import faizanshahzaddar.ultimoprogetto.exceptions.NotFoundException;
import faizanshahzaddar.ultimoprogetto.exceptions.UnauthorizedException;
import faizanshahzaddar.ultimoprogetto.payloads.LoginDTO;
import faizanshahzaddar.ultimoprogetto.security.TokenTools;
import jakarta.persistence.Table;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UtenteService utenteService;
    private final TokenTools tokenTools;
    private final PasswordEncoder bcrypt;

    public AuthService(UtenteService utenteService, TokenTools tokenTools, PasswordEncoder bcrypt) {
        this.utenteService = utenteService;
        this.tokenTools = tokenTools;
        this.bcrypt = bcrypt;
    }

    public String checkCredentialsAndGenerateToken(LoginDTO body) {

        try {
            Utente found = this.utenteService.findByEmail(body.email());
            if (this.bcrypt.matches(body.password(), found.getPassword())) {
                return this.tokenTools.generateToken(found);

            } else {
                // 3. Altrimenti -> Error
                throw new UnauthorizedException("Credenziali errate");
            }
        } catch (NotFoundException ex) {
            throw new UnauthorizedException("Credenziali errate");
        }
    }
}

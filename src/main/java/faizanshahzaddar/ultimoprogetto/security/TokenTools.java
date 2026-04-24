package faizanshahzaddar.ultimoprogetto.security;


import faizanshahzaddar.ultimoprogetto.entities.Utente;
import faizanshahzaddar.ultimoprogetto.exceptions.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class TokenTools {

    private final String segreta;

    public TokenTools(@Value("${jwt.secret}") String segreta) {
        this.segreta = segreta;
    }


    public String generateToken(Utente utente) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .subject(String.valueOf(utente.getId()))
                .signWith(Keys.hmacShaKeyFor(segreta.getBytes()))
                .compact();
    }


    public void verifyToken(String token) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(segreta.getBytes())).build().parse(token);
        } catch (Exception ex) {
            throw new UnauthorizedException("Problemi col token! Effettua di nuovo il login!");
        }
    }


    public UUID extractIdFromToken(String token) {
        return UUID.fromString(Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(segreta.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject());
    }
}

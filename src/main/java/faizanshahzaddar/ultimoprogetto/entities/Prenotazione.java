package faizanshahzaddar.ultimoprogetto.entities;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "prenotazioni", uniqueConstraints=@UniqueConstraint(columnNames = {"utente_id", "evento_id"}))
public class Prenotazione {
    @Id
    @GeneratedValue
    private UUID id;


    @Column(nullable = false)
    private LocalDate data;
    @Column(nullable = false)
    private int nPosti;

   @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

   @ManyToOne
    @JoinColumn(name = "evento_id",nullable = false)
    private Evento evento;


   protected Prenotazione(){};
    public Prenotazione(int nPosti, Evento evento, Utente utente) {
        this.data = LocalDate.now();
        this.nPosti = nPosti;
        this.evento = evento;
        this.utente = utente;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getnPosti() {
        return nPosti;
    }

    public void setnPosti(int nPosti) {
        this.nPosti = nPosti;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public UUID getId() {
        return id;
    }
}

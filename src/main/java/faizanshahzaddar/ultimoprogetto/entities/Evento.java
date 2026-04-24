package faizanshahzaddar.ultimoprogetto.entities;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "eventi")
public class Evento {

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private String titolo;
    @Column
    private String descrizione;
    @Column
    private LocalDate data;
    @Column
    private String luogo;
    @Column
    private int nPostiDisponibili;

    @ManyToOne
    @JoinColumn(name = "organizzatore_id")
    private Utente organizzatore;


    protected Evento(){};

    public Evento(String titolo, String descrizione, LocalDate data, String luogo, int nPostiDisponibili, Utente organizzatore) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.data = data;
        this.luogo = luogo;
        this.nPostiDisponibili = nPostiDisponibili;
        this.organizzatore = organizzatore;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public int getnPostiDisponibili() {
        return nPostiDisponibili;
    }

    public void setnPostiDisponibili(int nPostiDisponibili) {
        this.nPostiDisponibili = nPostiDisponibili;
    }
    public Utente getOrganizzatore() {
        return organizzatore;
    }

    public void setOrganizzatore(Utente organizzatore) {
        this.organizzatore = organizzatore;
    }

    public UUID getId() {
        return id;
    }


}

package it.unisa.scanapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A KForN.
 */
@Entity
@Table(name = "k_for_n")
public class KForN implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "inizio", nullable = false)
    private LocalDate inizio;

    @NotNull
    @Column(name = "fine", nullable = false)
    private LocalDate fine;

    @NotNull
    @Min(value = 0)
    @Column(name = "condizione", nullable = false)
    private Integer condizione;

    @NotNull
    @Min(value = 0)
    @Column(name = "omaggio", nullable = false)
    private Integer omaggio;

    @OneToOne(mappedBy = "kForN")
    @JsonIgnore
    private Articolo articolo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getInizio() {
        return inizio;
    }

    public KForN inizio(LocalDate inizio) {
        this.inizio = inizio;
        return this;
    }

    public void setInizio(LocalDate inizio) {
        this.inizio = inizio;
    }

    public LocalDate getFine() {
        return fine;
    }

    public KForN fine(LocalDate fine) {
        this.fine = fine;
        return this;
    }

    public void setFine(LocalDate fine) {
        this.fine = fine;
    }

    public Integer getCondizione() {
        return condizione;
    }

    public KForN condizione(Integer condizione) {
        this.condizione = condizione;
        return this;
    }

    public void setCondizione(Integer condizione) {
        this.condizione = condizione;
    }

    public Integer getOmaggio() {
        return omaggio;
    }

    public KForN omaggio(Integer omaggio) {
        this.omaggio = omaggio;
        return this;
    }

    public void setOmaggio(Integer omaggio) {
        this.omaggio = omaggio;
    }

    public Articolo getArticolo() {
        return articolo;
    }

    public KForN articolo(Articolo articolo) {
        this.articolo = articolo;
        return this;
    }

    public void setArticolo(Articolo articolo) {
        this.articolo = articolo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KForN)) {
            return false;
        }
        return id != null && id.equals(((KForN) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "KForN{" +
            "id=" + getId() +
            ", inizio='" + getInizio() + "'" +
            ", fine='" + getFine() + "'" +
            ", condizione=" + getCondizione() +
            ", omaggio=" + getOmaggio() +
            "}";
    }
}

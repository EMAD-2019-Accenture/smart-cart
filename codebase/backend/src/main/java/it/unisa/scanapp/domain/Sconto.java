package it.unisa.scanapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Sconto.
 */
@Entity
@Table(name = "sconto")
public class Sconto implements Serializable {

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
    @DecimalMin(value = "0")
    @Column(name = "ammontare", nullable = false)
    private Double ammontare;

    @OneToOne(mappedBy = "sconto")
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

    public Sconto inizio(LocalDate inizio) {
        this.inizio = inizio;
        return this;
    }

    public void setInizio(LocalDate inizio) {
        this.inizio = inizio;
    }

    public LocalDate getFine() {
        return fine;
    }

    public Sconto fine(LocalDate fine) {
        this.fine = fine;
        return this;
    }

    public void setFine(LocalDate fine) {
        this.fine = fine;
    }

    public Double getAmmontare() {
        return ammontare;
    }

    public Sconto ammontare(Double ammontare) {
        this.ammontare = ammontare;
        return this;
    }

    public void setAmmontare(Double ammontare) {
        this.ammontare = ammontare;
    }

    public Articolo getArticolo() {
        return articolo;
    }

    public Sconto articolo(Articolo articolo) {
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
        if (!(o instanceof Sconto)) {
            return false;
        }
        return id != null && id.equals(((Sconto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Sconto{" +
            "id=" + getId() +
            ", inizio='" + getInizio() + "'" +
            ", fine='" + getFine() + "'" +
            ", ammontare=" + getAmmontare() +
            "}";
    }
}

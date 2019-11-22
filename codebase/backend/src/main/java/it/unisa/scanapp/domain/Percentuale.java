package it.unisa.scanapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Percentuale.
 */
@Entity
@Table(name = "percentuale")
public class Percentuale implements Serializable {

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
    @DecimalMax(value = "100")
    @Column(name = "valore", nullable = false)
    private Double valore;

    @OneToOne(mappedBy = "percentuale")
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

    public Percentuale inizio(LocalDate inizio) {
        this.inizio = inizio;
        return this;
    }

    public void setInizio(LocalDate inizio) {
        this.inizio = inizio;
    }

    public LocalDate getFine() {
        return fine;
    }

    public Percentuale fine(LocalDate fine) {
        this.fine = fine;
        return this;
    }

    public void setFine(LocalDate fine) {
        this.fine = fine;
    }

    public Double getValore() {
        return valore;
    }

    public Percentuale valore(Double valore) {
        this.valore = valore;
        return this;
    }

    public void setValore(Double valore) {
        this.valore = valore;
    }

    public Articolo getArticolo() {
        return articolo;
    }

    public Percentuale articolo(Articolo articolo) {
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
        if (!(o instanceof Percentuale)) {
            return false;
        }
        return id != null && id.equals(((Percentuale) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Percentuale{" +
            "id=" + getId() +
            ", inizio='" + getInizio() + "'" +
            ", fine='" + getFine() + "'" +
            ", valore=" + getValore() +
            "}";
    }
}

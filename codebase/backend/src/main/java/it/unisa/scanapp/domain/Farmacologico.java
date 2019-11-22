package it.unisa.scanapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Farmacologico.
 */
@Entity
@Table(name = "farmacologico")
public class Farmacologico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "assunzione", nullable = false)
    private String assunzione;

    @NotNull
    @Min(value = 0)
    @Column(name = "giorni_alla_scadenza", nullable = false)
    private Integer giorniAllaScadenza;

    @NotNull
    @Column(name = "avvertenze", nullable = false)
    private String avvertenze;

    @OneToOne(mappedBy = "farmacologico")
    @JsonIgnore
    private Articolo articolo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssunzione() {
        return assunzione;
    }

    public Farmacologico assunzione(String assunzione) {
        this.assunzione = assunzione;
        return this;
    }

    public void setAssunzione(String assunzione) {
        this.assunzione = assunzione;
    }

    public Integer getGiorniAllaScadenza() {
        return giorniAllaScadenza;
    }

    public Farmacologico giorniAllaScadenza(Integer giorniAllaScadenza) {
        this.giorniAllaScadenza = giorniAllaScadenza;
        return this;
    }

    public void setGiorniAllaScadenza(Integer giorniAllaScadenza) {
        this.giorniAllaScadenza = giorniAllaScadenza;
    }

    public String getAvvertenze() {
        return avvertenze;
    }

    public Farmacologico avvertenze(String avvertenze) {
        this.avvertenze = avvertenze;
        return this;
    }

    public void setAvvertenze(String avvertenze) {
        this.avvertenze = avvertenze;
    }

    public Articolo getArticolo() {
        return articolo;
    }

    public Farmacologico articolo(Articolo articolo) {
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
        if (!(o instanceof Farmacologico)) {
            return false;
        }
        return id != null && id.equals(((Farmacologico) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Farmacologico{" +
            "id=" + getId() +
            ", assunzione='" + getAssunzione() + "'" +
            ", giorniAllaScadenza=" + getGiorniAllaScadenza() +
            ", avvertenze='" + getAvvertenze() + "'" +
            "}";
    }
}

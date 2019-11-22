package it.unisa.scanapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Vestiario.
 */
@Entity
@Table(name = "vestiario")
public class Vestiario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "taglia", nullable = false)
    private String taglia;

    @NotNull
    @Column(name = "colore", nullable = false)
    private String colore;

    @NotNull
    @Column(name = "materiale", nullable = false)
    private String materiale;

    @NotNull
    @Column(name = "staglione", nullable = false)
    private String staglione;

    @OneToOne(mappedBy = "vestiario")
    @JsonIgnore
    private Articolo articolo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaglia() {
        return taglia;
    }

    public Vestiario taglia(String taglia) {
        this.taglia = taglia;
        return this;
    }

    public void setTaglia(String taglia) {
        this.taglia = taglia;
    }

    public String getColore() {
        return colore;
    }

    public Vestiario colore(String colore) {
        this.colore = colore;
        return this;
    }

    public void setColore(String colore) {
        this.colore = colore;
    }

    public String getMateriale() {
        return materiale;
    }

    public Vestiario materiale(String materiale) {
        this.materiale = materiale;
        return this;
    }

    public void setMateriale(String materiale) {
        this.materiale = materiale;
    }

    public String getStaglione() {
        return staglione;
    }

    public Vestiario staglione(String staglione) {
        this.staglione = staglione;
        return this;
    }

    public void setStaglione(String staglione) {
        this.staglione = staglione;
    }

    public Articolo getArticolo() {
        return articolo;
    }

    public Vestiario articolo(Articolo articolo) {
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
        if (!(o instanceof Vestiario)) {
            return false;
        }
        return id != null && id.equals(((Vestiario) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Vestiario{" +
            "id=" + getId() +
            ", taglia='" + getTaglia() + "'" +
            ", colore='" + getColore() + "'" +
            ", materiale='" + getMateriale() + "'" +
            ", staglione='" + getStaglione() + "'" +
            "}";
    }
}

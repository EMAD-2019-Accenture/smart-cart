package it.unisa.scanapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Transazione.
 */
@Entity
@Table(name = "transazione")
public class Transazione implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "data", nullable = false)
    private LocalDate data;

    @OneToMany(mappedBy = "transazione")
    private Set<Articolo> articolos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("transaziones")
    private Acquirente acquirente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public Transazione data(LocalDate data) {
        this.data = data;
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Set<Articolo> getArticolos() {
        return articolos;
    }

    public Transazione articolos(Set<Articolo> articolos) {
        this.articolos = articolos;
        return this;
    }

    public Transazione addArticolo(Articolo articolo) {
        this.articolos.add(articolo);
        articolo.setTransazione(this);
        return this;
    }

    public Transazione removeArticolo(Articolo articolo) {
        this.articolos.remove(articolo);
        articolo.setTransazione(null);
        return this;
    }

    public void setArticolos(Set<Articolo> articolos) {
        this.articolos = articolos;
    }

    public Acquirente getAcquirente() {
        return acquirente;
    }

    public Transazione acquirente(Acquirente acquirente) {
        this.acquirente = acquirente;
        return this;
    }

    public void setAcquirente(Acquirente acquirente) {
        this.acquirente = acquirente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transazione)) {
            return false;
        }
        return id != null && id.equals(((Transazione) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Transazione{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            "}";
    }
}

package it.unisa.scanapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Allergene.
 */
@Entity
@Table(name = "allergene")
public class Allergene implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @ManyToMany(mappedBy = "allergenes")
    @JsonIgnore
    private Set<Alimentare> alimentares = new HashSet<>();

    @ManyToMany(mappedBy = "allergenes")
    @JsonIgnore
    private Set<Acquirente> acquirentes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Allergene nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Alimentare> getAlimentares() {
        return alimentares;
    }

    public Allergene alimentares(Set<Alimentare> alimentares) {
        this.alimentares = alimentares;
        return this;
    }

    public Allergene addAlimentare(Alimentare alimentare) {
        this.alimentares.add(alimentare);
        alimentare.getAllergenes().add(this);
        return this;
    }

    public Allergene removeAlimentare(Alimentare alimentare) {
        this.alimentares.remove(alimentare);
        alimentare.getAllergenes().remove(this);
        return this;
    }

    public void setAlimentares(Set<Alimentare> alimentares) {
        this.alimentares = alimentares;
    }

    public Set<Acquirente> getAcquirentes() {
        return acquirentes;
    }

    public Allergene acquirentes(Set<Acquirente> acquirentes) {
        this.acquirentes = acquirentes;
        return this;
    }

    public Allergene addAcquirente(Acquirente acquirente) {
        this.acquirentes.add(acquirente);
        acquirente.getAllergenes().add(this);
        return this;
    }

    public Allergene removeAcquirente(Acquirente acquirente) {
        this.acquirentes.remove(acquirente);
        acquirente.getAllergenes().remove(this);
        return this;
    }

    public void setAcquirentes(Set<Acquirente> acquirentes) {
        this.acquirentes = acquirentes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Allergene)) {
            return false;
        }
        return id != null && id.equals(((Allergene) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Allergene{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}

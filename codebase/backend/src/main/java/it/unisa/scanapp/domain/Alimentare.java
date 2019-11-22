package it.unisa.scanapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Alimentare.
 */
@Entity
@Table(name = "alimentare")
public class Alimentare implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Min(value = 0)
    @Column(name = "giorni_alla_scadenza", nullable = false)
    private Integer giorniAllaScadenza;

    @Column(name = "stagione")
    private String stagione;

    @Column(name = "bio")
    private Boolean bio;

    @ManyToMany
    @JoinTable(name = "alimentare_allergene",
               joinColumns = @JoinColumn(name = "alimentare_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "allergene_id", referencedColumnName = "id"))
    private Set<Allergene> allergenes = new HashSet<>();

    @OneToOne(mappedBy = "alimentare")
    @JsonIgnore
    private Articolo articolo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGiorniAllaScadenza() {
        return giorniAllaScadenza;
    }

    public Alimentare giorniAllaScadenza(Integer giorniAllaScadenza) {
        this.giorniAllaScadenza = giorniAllaScadenza;
        return this;
    }

    public void setGiorniAllaScadenza(Integer giorniAllaScadenza) {
        this.giorniAllaScadenza = giorniAllaScadenza;
    }

    public String getStagione() {
        return stagione;
    }

    public Alimentare stagione(String stagione) {
        this.stagione = stagione;
        return this;
    }

    public void setStagione(String stagione) {
        this.stagione = stagione;
    }

    public Boolean isBio() {
        return bio;
    }

    public Alimentare bio(Boolean bio) {
        this.bio = bio;
        return this;
    }

    public void setBio(Boolean bio) {
        this.bio = bio;
    }

    public Set<Allergene> getAllergenes() {
        return allergenes;
    }

    public Alimentare allergenes(Set<Allergene> allergenes) {
        this.allergenes = allergenes;
        return this;
    }

    public Alimentare addAllergene(Allergene allergene) {
        this.allergenes.add(allergene);
        allergene.getAlimentares().add(this);
        return this;
    }

    public Alimentare removeAllergene(Allergene allergene) {
        this.allergenes.remove(allergene);
        allergene.getAlimentares().remove(this);
        return this;
    }

    public void setAllergenes(Set<Allergene> allergenes) {
        this.allergenes = allergenes;
    }

    public Articolo getArticolo() {
        return articolo;
    }

    public Alimentare articolo(Articolo articolo) {
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
        if (!(o instanceof Alimentare)) {
            return false;
        }
        return id != null && id.equals(((Alimentare) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Alimentare{" +
            "id=" + getId() +
            ", giorniAllaScadenza=" + getGiorniAllaScadenza() +
            ", stagione='" + getStagione() + "'" +
            ", bio='" + isBio() + "'" +
            "}";
    }
}

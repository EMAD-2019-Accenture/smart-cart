package it.unisa.scanapp.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Acquirente.
 */
@Entity
@Table(name = "acquirente")
public class Acquirente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Pattern(regexp = "[A-Za-z]")
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Pattern(regexp = "[A-Za-z]")
    @Column(name = "cognome", nullable = false)
    private String cognome;

    @Column(name = "data_nascita")
    private LocalDate dataNascita;

    @Column(name = "nazionalita")
    private String nazionalita;

    @Column(name = "vegano")
    private Boolean vegano;

    @Column(name = "vegetariano")
    private Boolean vegetariano;

    @Column(name = "celiaco")
    private Boolean celiaco;

    @OneToMany(mappedBy = "acquirente")
    private Set<Transazione> transaziones = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "acquirente_allergene",
               joinColumns = @JoinColumn(name = "acquirente_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "allergene_id", referencedColumnName = "id"))
    private Set<Allergene> allergenes = new HashSet<>();

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

    public Acquirente nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public Acquirente cognome(String cognome) {
        this.cognome = cognome;
        return this;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public Acquirente dataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
        return this;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getNazionalita() {
        return nazionalita;
    }

    public Acquirente nazionalita(String nazionalita) {
        this.nazionalita = nazionalita;
        return this;
    }

    public void setNazionalita(String nazionalita) {
        this.nazionalita = nazionalita;
    }

    public Boolean isVegano() {
        return vegano;
    }

    public Acquirente vegano(Boolean vegano) {
        this.vegano = vegano;
        return this;
    }

    public void setVegano(Boolean vegano) {
        this.vegano = vegano;
    }

    public Boolean isVegetariano() {
        return vegetariano;
    }

    public Acquirente vegetariano(Boolean vegetariano) {
        this.vegetariano = vegetariano;
        return this;
    }

    public void setVegetariano(Boolean vegetariano) {
        this.vegetariano = vegetariano;
    }

    public Boolean isCeliaco() {
        return celiaco;
    }

    public Acquirente celiaco(Boolean celiaco) {
        this.celiaco = celiaco;
        return this;
    }

    public void setCeliaco(Boolean celiaco) {
        this.celiaco = celiaco;
    }

    public Set<Transazione> getTransaziones() {
        return transaziones;
    }

    public Acquirente transaziones(Set<Transazione> transaziones) {
        this.transaziones = transaziones;
        return this;
    }

    public Acquirente addTransazione(Transazione transazione) {
        this.transaziones.add(transazione);
        transazione.setAcquirente(this);
        return this;
    }

    public Acquirente removeTransazione(Transazione transazione) {
        this.transaziones.remove(transazione);
        transazione.setAcquirente(null);
        return this;
    }

    public void setTransaziones(Set<Transazione> transaziones) {
        this.transaziones = transaziones;
    }

    public Set<Allergene> getAllergenes() {
        return allergenes;
    }

    public Acquirente allergenes(Set<Allergene> allergenes) {
        this.allergenes = allergenes;
        return this;
    }

    public Acquirente addAllergene(Allergene allergene) {
        this.allergenes.add(allergene);
        allergene.getAcquirentes().add(this);
        return this;
    }

    public Acquirente removeAllergene(Allergene allergene) {
        this.allergenes.remove(allergene);
        allergene.getAcquirentes().remove(this);
        return this;
    }

    public void setAllergenes(Set<Allergene> allergenes) {
        this.allergenes = allergenes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Acquirente)) {
            return false;
        }
        return id != null && id.equals(((Acquirente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Acquirente{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cognome='" + getCognome() + "'" +
            ", dataNascita='" + getDataNascita() + "'" +
            ", nazionalita='" + getNazionalita() + "'" +
            ", vegano='" + isVegano() + "'" +
            ", vegetariano='" + isVegetariano() + "'" +
            ", celiaco='" + isCeliaco() + "'" +
            "}";
    }
}

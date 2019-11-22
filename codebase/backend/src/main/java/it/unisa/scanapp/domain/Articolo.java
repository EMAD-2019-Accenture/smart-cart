package it.unisa.scanapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Articolo.
 */
@Entity
@Table(name = "articolo")
public class Articolo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Pattern(regexp = "^\\d{13}$")
    @Column(name = "barcode", nullable = false)
    private String barcode;

    @NotNull
    @Column(name = "descrizione", nullable = false)
    private String descrizione;

    @Column(name = "etichetta")
    private String etichetta;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "prezzo", nullable = false)
    private Double prezzo;

    @NotNull
    @Column(name = "marchio", nullable = false)
    private String marchio;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "peso_lordo", nullable = false)
    private Double pesoLordo;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "peso_netto", nullable = false)
    private Double pesoNetto;

    @NotNull
    @Column(name = "provenienza", nullable = false)
    private String provenienza;

    @NotNull
    @Min(value = 0)
    @Column(name = "quantita", nullable = false)
    private Integer quantita;

    @OneToOne
    @JoinColumn(unique = true)
    private Sconto sconto;

    @OneToOne
    @JoinColumn(unique = true)
    private Percentuale percentuale;

    @OneToOne
    @JoinColumn(unique = true)
    private KForN kForN;

    @OneToOne
    @JoinColumn(unique = true)
    private Alimentare alimentare;

    @OneToOne
    @JoinColumn(unique = true)
    private Farmacologico farmacologico;

    @OneToOne
    @JoinColumn(unique = true)
    private Vestiario vestiario;

    @OneToMany(mappedBy = "articolo")
    private Set<Categoria> categorias = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("articolos")
    private Transazione transazione;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public Articolo barcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public Articolo descrizione(String descrizione) {
        this.descrizione = descrizione;
        return this;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getEtichetta() {
        return etichetta;
    }

    public Articolo etichetta(String etichetta) {
        this.etichetta = etichetta;
        return this;
    }

    public void setEtichetta(String etichetta) {
        this.etichetta = etichetta;
    }

    public Double getPrezzo() {
        return prezzo;
    }

    public Articolo prezzo(Double prezzo) {
        this.prezzo = prezzo;
        return this;
    }

    public void setPrezzo(Double prezzo) {
        this.prezzo = prezzo;
    }

    public String getMarchio() {
        return marchio;
    }

    public Articolo marchio(String marchio) {
        this.marchio = marchio;
        return this;
    }

    public void setMarchio(String marchio) {
        this.marchio = marchio;
    }

    public Double getPesoLordo() {
        return pesoLordo;
    }

    public Articolo pesoLordo(Double pesoLordo) {
        this.pesoLordo = pesoLordo;
        return this;
    }

    public void setPesoLordo(Double pesoLordo) {
        this.pesoLordo = pesoLordo;
    }

    public Double getPesoNetto() {
        return pesoNetto;
    }

    public Articolo pesoNetto(Double pesoNetto) {
        this.pesoNetto = pesoNetto;
        return this;
    }

    public void setPesoNetto(Double pesoNetto) {
        this.pesoNetto = pesoNetto;
    }

    public String getProvenienza() {
        return provenienza;
    }

    public Articolo provenienza(String provenienza) {
        this.provenienza = provenienza;
        return this;
    }

    public void setProvenienza(String provenienza) {
        this.provenienza = provenienza;
    }

    public Integer getQuantita() {
        return quantita;
    }

    public Articolo quantita(Integer quantita) {
        this.quantita = quantita;
        return this;
    }

    public void setQuantita(Integer quantita) {
        this.quantita = quantita;
    }

    public Sconto getSconto() {
        return sconto;
    }

    public Articolo sconto(Sconto sconto) {
        this.sconto = sconto;
        return this;
    }

    public void setSconto(Sconto sconto) {
        this.sconto = sconto;
    }

    public Percentuale getPercentuale() {
        return percentuale;
    }

    public Articolo percentuale(Percentuale percentuale) {
        this.percentuale = percentuale;
        return this;
    }

    public void setPercentuale(Percentuale percentuale) {
        this.percentuale = percentuale;
    }

    public KForN getKForN() {
        return kForN;
    }

    public Articolo kForN(KForN kForN) {
        this.kForN = kForN;
        return this;
    }

    public void setKForN(KForN kForN) {
        this.kForN = kForN;
    }

    public Alimentare getAlimentare() {
        return alimentare;
    }

    public Articolo alimentare(Alimentare alimentare) {
        this.alimentare = alimentare;
        return this;
    }

    public void setAlimentare(Alimentare alimentare) {
        this.alimentare = alimentare;
    }

    public Farmacologico getFarmacologico() {
        return farmacologico;
    }

    public Articolo farmacologico(Farmacologico farmacologico) {
        this.farmacologico = farmacologico;
        return this;
    }

    public void setFarmacologico(Farmacologico farmacologico) {
        this.farmacologico = farmacologico;
    }

    public Vestiario getVestiario() {
        return vestiario;
    }

    public Articolo vestiario(Vestiario vestiario) {
        this.vestiario = vestiario;
        return this;
    }

    public void setVestiario(Vestiario vestiario) {
        this.vestiario = vestiario;
    }

    public Set<Categoria> getCategorias() {
        return categorias;
    }

    public Articolo categorias(Set<Categoria> categorias) {
        this.categorias = categorias;
        return this;
    }

    public Articolo addCategoria(Categoria categoria) {
        this.categorias.add(categoria);
        categoria.setArticolo(this);
        return this;
    }

    public Articolo removeCategoria(Categoria categoria) {
        this.categorias.remove(categoria);
        categoria.setArticolo(null);
        return this;
    }

    public void setCategorias(Set<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Transazione getTransazione() {
        return transazione;
    }

    public Articolo transazione(Transazione transazione) {
        this.transazione = transazione;
        return this;
    }

    public void setTransazione(Transazione transazione) {
        this.transazione = transazione;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Articolo)) {
            return false;
        }
        return id != null && id.equals(((Articolo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Articolo{" +
            "id=" + getId() +
            ", barcode='" + getBarcode() + "'" +
            ", descrizione='" + getDescrizione() + "'" +
            ", etichetta='" + getEtichetta() + "'" +
            ", prezzo=" + getPrezzo() +
            ", marchio='" + getMarchio() + "'" +
            ", pesoLordo=" + getPesoLordo() +
            ", pesoNetto=" + getPesoNetto() +
            ", provenienza='" + getProvenienza() + "'" +
            ", quantita=" + getQuantita() +
            "}";
    }
}

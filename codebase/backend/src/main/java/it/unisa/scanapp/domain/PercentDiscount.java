package it.unisa.scanapp.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A PercentDiscount.
 */
@Entity
@Table(name = "perc_discount")
public class PercentDiscount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_start", nullable = false)
    private LocalDate start;

    @NotNull
    @Column(name = "jhi_end", nullable = false)
    private LocalDate end;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "jhi_value", nullable = false)
    private Double value;

    @OneToMany(mappedBy = "percentDiscount")
    private Set<Product> products = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStart() {
        return start;
    }

    public PercentDiscount start(LocalDate start) {
        this.start = start;
        return this;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public PercentDiscount end(LocalDate end) {
        this.end = end;
        return this;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public Double getValue() {
        return value;
    }

    public PercentDiscount value(Double value) {
        this.value = value;
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public PercentDiscount products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public PercentDiscount addProduct(Product product) {
        this.products.add(product);
        product.setPercentDiscount(this);
        return this;
    }

    public PercentDiscount removeProduct(Product product) {
        this.products.remove(product);
        product.setPercentDiscount(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PercentDiscount)) {
            return false;
        }
        return id != null && id.equals(((PercentDiscount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PercentDiscount{" +
            "id=" + getId() +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            ", value=" + getValue() +
            "}";
    }
}

package it.unisa.scanapp.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A KForN.
 */
@Entity
@Table(name = "k_for_n")
public class KForN implements Serializable {

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
    @Min(value = 0)
    @Column(name = "condition", nullable = false)
    private Integer condition;

    @NotNull
    @Min(value = 0)
    @Column(name = "free", nullable = false)
    private Integer free;

    @OneToMany(mappedBy = "kForN")
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

    public KForN start(LocalDate start) {
        this.start = start;
        return this;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public KForN end(LocalDate end) {
        this.end = end;
        return this;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public Integer getCondition() {
        return condition;
    }

    public KForN condition(Integer condition) {
        this.condition = condition;
        return this;
    }

    public void setCondition(Integer condition) {
        this.condition = condition;
    }

    public Integer getFree() {
        return free;
    }

    public KForN free(Integer free) {
        this.free = free;
        return this;
    }

    public void setFree(Integer free) {
        this.free = free;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public KForN products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public KForN addProduct(Product product) {
        this.products.add(product);
        product.setKForN(this);
        return this;
    }

    public KForN removeProduct(Product product) {
        this.products.remove(product);
        product.setKForN(null);
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
        if (!(o instanceof KForN)) {
            return false;
        }
        return id != null && id.equals(((KForN) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "KForN{" +
            "id=" + getId() +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            ", condition=" + getCondition() +
            ", free=" + getFree() +
            "}";
    }
}

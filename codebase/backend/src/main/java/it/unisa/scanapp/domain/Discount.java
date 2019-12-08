package it.unisa.scanapp.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import it.unisa.scanapp.domain.enumeration.DiscountType;

/**
 * A Discount.
 */
@Entity
@Table(name = "discount")
public class Discount implements Serializable {

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
    @Column(name = "amount", nullable = false)
    private Double amount;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "pervent_value", nullable = false)
    private Double perventValue;

    @NotNull
    @Min(value = 0)
    @Column(name = "condition", nullable = false)
    private Integer condition;

    @NotNull
    @Min(value = 0)
    @Column(name = "free", nullable = false)
    private Integer free;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private DiscountType type;

    @OneToMany(mappedBy = "discount")
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

    public Discount start(LocalDate start) {
        this.start = start;
        return this;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public Discount end(LocalDate end) {
        this.end = end;
        return this;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public Double getAmount() {
        return amount;
    }

    public Discount amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getPerventValue() {
        return perventValue;
    }

    public Discount perventValue(Double perventValue) {
        this.perventValue = perventValue;
        return this;
    }

    public void setPerventValue(Double perventValue) {
        this.perventValue = perventValue;
    }

    public Integer getCondition() {
        return condition;
    }

    public Discount condition(Integer condition) {
        this.condition = condition;
        return this;
    }

    public void setCondition(Integer condition) {
        this.condition = condition;
    }

    public Integer getFree() {
        return free;
    }

    public Discount free(Integer free) {
        this.free = free;
        return this;
    }

    public void setFree(Integer free) {
        this.free = free;
    }

    public DiscountType getType() {
        return type;
    }

    public Discount type(DiscountType type) {
        this.type = type;
        return this;
    }

    public void setType(DiscountType type) {
        this.type = type;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Discount products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public Discount addProduct(Product product) {
        this.products.add(product);
        product.setDiscount(this);
        return this;
    }

    public Discount removeProduct(Product product) {
        this.products.remove(product);
        product.setDiscount(null);
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
        if (!(o instanceof Discount)) {
            return false;
        }
        return id != null && id.equals(((Discount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Discount{" +
            "id=" + getId() +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            ", amount=" + getAmount() +
            ", perventValue=" + getPerventValue() +
            ", condition=" + getCondition() +
            ", free=" + getFree() +
            ", type='" + getType() + "'" +
            "}";
    }
}

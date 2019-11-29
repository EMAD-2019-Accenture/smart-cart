package it.unisa.scanapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Allergen.
 */
@Entity
@Table(name = "allergen")
public class Allergen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "allergens")
    @JsonIgnore
    private Set<Product> products = new HashSet<>();

    @ManyToMany(mappedBy = "allergens")
    @JsonIgnore
    private Set<Customer> customers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Allergen name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Allergen products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public Allergen addProduct(Product product) {
        this.products.add(product);
        product.getAllergens().add(this);
        return this;
    }

    public Allergen removeProduct(Product product) {
        this.products.remove(product);
        product.getAllergens().remove(this);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public Allergen customers(Set<Customer> customers) {
        this.customers = customers;
        return this;
    }

    public Allergen addCustomer(Customer customer) {
        this.customers.add(customer);
        customer.getAllergens().add(this);
        return this;
    }

    public Allergen removeCustomer(Customer customer) {
        this.customers.remove(customer);
        customer.getAllergens().remove(this);
        return this;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Allergen)) {
            return false;
        }
        return id != null && id.equals(((Allergen) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Allergen{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}

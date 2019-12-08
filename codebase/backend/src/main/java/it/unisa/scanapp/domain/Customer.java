package it.unisa.scanapp.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "birth")
    private LocalDate birth;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "vegan")
    private Boolean vegan;

    @Column(name = "vegetarian")
    private Boolean vegetarian;

    @Column(name = "celiac")
    private Boolean celiac;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public Customer birth(LocalDate birth) {
        this.birth = birth;
        return this;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public String getNationality() {
        return nationality;
    }

    public Customer nationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Boolean isVegan() {
        return vegan;
    }

    public Customer vegan(Boolean vegan) {
        this.vegan = vegan;
        return this;
    }

    public void setVegan(Boolean vegan) {
        this.vegan = vegan;
    }

    public Boolean isVegetarian() {
        return vegetarian;
    }

    public Customer vegetarian(Boolean vegetarian) {
        this.vegetarian = vegetarian;
        return this;
    }

    public void setVegetarian(Boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public Boolean isCeliac() {
        return celiac;
    }

    public Customer celiac(Boolean celiac) {
        this.celiac = celiac;
        return this;
    }

    public void setCeliac(Boolean celiac) {
        this.celiac = celiac;
    }

    public User getUser() {
        return user;
    }

    public Customer user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", birth='" + getBirth() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", vegan='" + isVegan() + "'" +
            ", vegetarian='" + isVegetarian() + "'" +
            ", celiac='" + isCeliac() + "'" +
            "}";
    }
}

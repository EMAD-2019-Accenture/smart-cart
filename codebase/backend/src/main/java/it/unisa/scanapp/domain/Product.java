package it.unisa.scanapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
public class Product implements Serializable {

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
    @Column(name = "name", nullable = false)
    private String name;

    
    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "price", nullable = false)
    private Double price;

    @NotNull
    @Column(name = "brand", nullable = false)
    private String brand;

    @Min(value = 0)
    @Column(name = "amount")
    private Integer amount;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "source")
    private String source;

    @Lob
    @Column(name = "ingredients")
    private String ingredients;

    @Lob
    @Column(name = "conservation")
    private String conservation;

    @Lob
    @Column(name = "preparation")
    private String preparation;

    @Lob
    @Column(name = "nutrients")
    private String nutrients;

    @OneToMany(mappedBy = "product")
    private Set<Category> categories = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Discount discount;

    @ManyToMany
    @JoinTable(name = "product_allergen",
               joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "allergen_id", referencedColumnName = "id"))
    private Set<Allergen> allergens = new HashSet<>();

    @ManyToMany(mappedBy = "products")
    @JsonIgnore
    private Set<Transaction> transactions = new HashSet<>();

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

    public Product barcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public Product price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public Product brand(String brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getAmount() {
        return amount;
    }

    public Product amount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Product imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSource() {
        return source;
    }

    public Product source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getIngredients() {
        return ingredients;
    }

    public Product ingredients(String ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getConservation() {
        return conservation;
    }

    public Product conservation(String conservation) {
        this.conservation = conservation;
        return this;
    }

    public void setConservation(String conservation) {
        this.conservation = conservation;
    }

    public String getPreparation() {
        return preparation;
    }

    public Product preparation(String preparation) {
        this.preparation = preparation;
        return this;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public String getNutrients() {
        return nutrients;
    }

    public Product nutrients(String nutrients) {
        this.nutrients = nutrients;
        return this;
    }

    public void setNutrients(String nutrients) {
        this.nutrients = nutrients;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Product categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public Product addCategory(Category category) {
        this.categories.add(category);
        category.setProduct(this);
        return this;
    }

    public Product removeCategory(Category category) {
        this.categories.remove(category);
        category.setProduct(null);
        return this;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Discount getDiscount() {
        return discount;
    }

    public Product discount(Discount discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Set<Allergen> getAllergens() {
        return allergens;
    }

    public Product allergens(Set<Allergen> allergens) {
        this.allergens = allergens;
        return this;
    }

    public Product addAllergen(Allergen allergen) {
        this.allergens.add(allergen);
        allergen.getProducts().add(this);
        return this;
    }

    public Product removeAllergen(Allergen allergen) {
        this.allergens.remove(allergen);
        allergen.getProducts().remove(this);
        return this;
    }

    public void setAllergens(Set<Allergen> allergens) {
        this.allergens = allergens;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public Product transactions(Set<Transaction> transactions) {
        this.transactions = transactions;
        return this;
    }

    public Product addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        transaction.getProducts().add(this);
        return this;
    }

    public Product removeTransaction(Transaction transaction) {
        this.transactions.remove(transaction);
        transaction.getProducts().remove(this);
        return this;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", barcode='" + getBarcode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", price=" + getPrice() +
            ", brand='" + getBrand() + "'" +
            ", amount=" + getAmount() +
            ", imageUrl='" + getImageUrl() + "'" +
            ", source='" + getSource() + "'" +
            ", ingredients='" + getIngredients() + "'" +
            ", conservation='" + getConservation() + "'" +
            ", preparation='" + getPreparation() + "'" +
            ", nutrients='" + getNutrients() + "'" +
            "}";
    }
}

import { Allergen, IAllergen } from './allergen';
import { Category, ICategory } from './category';
import { Discount, IDiscount } from './discount';
import { IKForN, KForN } from './k-for-n';
import { IPercentDiscount, PercentDiscount } from './percent-discount';

export interface IProduct {
    id: number;
    barcode: string;
    name: string;
    description: string;
    price: number;
    brand: string;
    amount: number;
    imageUrl: string;
    source: string;
    ingredients: string;
    conservation: string;
    preparation: string;
    nutrients: string;
    category: ICategory;
    discount: IDiscount;
    percentDiscount: IPercentDiscount;
    allergens: IAllergen[];
    kForN: IKForN;
}

export class Product {
    private id: number;
    private barcode: string;
    private name: string;
    private description: string;
    private price: number;
    private brand: string;
    private amount: number;
    private imageUrl: string;
    private source: string;
    private ingredients: string;
    private conservation: string;
    private preparation: string;
    private nutrients: string;
    private category: Category;
    private discount: Discount;
    private percentDiscount: PercentDiscount;
    private allergens: Array<Allergen>;
    private kForN: KForN;

    constructor(product?: IProduct) {
        this.allergens = new Array<Allergen>();
        if (product) {
            this.id = product.id;
            this.barcode = product.barcode;
            this.name = product.name;
            this.description = product.description;
            this.price = product.price;
            this.brand = product.brand;
            this.amount = product.amount;
            this.imageUrl = product.imageUrl;
            this.source = product.source;
            this.ingredients = product.ingredients;
            this.conservation = product.conservation;
            this.preparation = product.preparation;
            this.nutrients = product.nutrients;
            this.category = new Category(product.category);
            this.discount = new Discount(product.discount);
            this.percentDiscount = new PercentDiscount(product.percentDiscount);
            if (product.allergens != null) {
                product.allergens.forEach(element => {
                    this.allergens.push(new Allergen(element));
                });
            }
            this.kForN = new KForN(product.kForN);
        }
    }

    public getDiscountedPrice(): number {
        let price: number = this.getPrice();
        if (this.getDiscount().getId()) {
            price -= this.getDiscount().getAmount();
        }
        if (this.getPercentDiscount().getId()) {
            price *= this.getPercentDiscount().getValue();
        }
        return price;
    }

    public getId(): number {
        return this.id;
    }

    public setId(id: number): void {
        this.id = id;
    }

    public getBarcode(): string {
        return this.barcode;
    }

    public setBarcode(barcode: string): void {
        this.barcode = barcode;
    }

    public getName(): string {
        return this.name;
    }

    public setName(name: string): void {
        this.name = name;
    }

    public getDescription(): string {
        return this.description;
    }

    public setDescription(description: string): void {
        this.description = description;
    }

    public getPrice(): number {
        return this.price;
    }

    public setPrice(price: number): void {
        this.price = price;
    }

    public getBrand(): string {
        return this.brand;
    }

    public setBrand(brand: string): void {
        this.brand = brand;
    }

    public getAmount(): number {
        return this.amount;
    }

    public setAmount(amount: number): void {
        this.amount = amount;
    }

    public getImageUrl(): string {
        return this.imageUrl;
    }

    public setImageUrl(imageUrl: string): void {
        this.imageUrl = imageUrl;
    }

    public getSource(): string {
        return this.source;
    }

    public setSource(source: string): void {
        this.source = source;
    }

    public getIngredients(): string {
        return this.ingredients;
    }

    public setIngredients(ingredients: string): void {
        this.ingredients = ingredients;
    }

    public getConservation(): string {
        return this.conservation;
    }

    public setConservation(conservation: string): void {
        this.conservation = conservation;
    }

    public getPreparation(): string {
        return this.preparation;
    }

    public setPreparation(preparation: string): void {
        this.preparation = preparation;
    }

    public getNutrients(): string {
        return this.nutrients;
    }

    public setNutrients(nutrients: string): void {
        this.nutrients = nutrients;
    }

    public getCategory(): Category {
        return this.category;
    }

    public setCategory(category: Category): void {
        this.category = category;
    }

    public getDiscount(): Discount {
        return this.discount;
    }

    public setDiscount(discount: Discount): void {
        this.discount = discount;
    }

    public getPercentDiscount(): PercentDiscount {
        return this.percentDiscount;
    }

    public setPercentDiscount(percentDiscount: PercentDiscount): void {
        this.percentDiscount = percentDiscount;
    }

    public getAllergens(): Array<Allergen> {
        return this.allergens;
    }

    public setAllergens(allergens: Array<Allergen>): void {
        this.allergens = allergens;
    }

    public getKForN(): KForN {
        return this.kForN;
    }

    public setKForN(kForN: KForN): void {
        this.kForN = kForN;
    }
}

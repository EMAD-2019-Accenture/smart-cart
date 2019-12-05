import { Allergen, IAllergen } from './allergen';

export interface IProduct {
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
    allergens: IAllergen[];
}

export class Product {
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
    private allergens: Array<Allergen>;

    constructor(product?: IProduct) {
        this.allergens = new Array<Allergen>();
        if (product) {
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
            product.allergens.forEach(element => {
                this.allergens.push(new Allergen(element));
            });
        }
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

    public getAllergens(): Array<Allergen> {
        return this.allergens;
    }

    public setAllergens(allergens: Array<Allergen>): void {
        this.allergens = allergens;
    }

}
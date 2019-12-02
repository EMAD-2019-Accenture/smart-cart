import { Allergen } from './allergen';

export class Product {
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
    allergens: Array<Allergen>;

    constructor() {

    }
}

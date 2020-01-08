import { IProduct, Product } from './product';

export interface IRecommendation {
    id: number;
    product: IProduct;
    date: Date;
}

export class Recommendation {
    private id: number;
    private product: Product;
    private date: Date;

    constructor(recommendation?: IRecommendation) {
        if (recommendation) {
            this.id = recommendation.id;
            this.product = new Product(recommendation.product);
            this.date = recommendation.date;
        }
    }

    public getId(): number {
        return this.id;
    }

    public setId(id: number): void {
        this.id = id;
    }

    public getProduct(): Product {
        return this.product;
    }

    public setProduct(product: Product): void {
        this.product = product;
    }

    public getDate(): Date {
        return this.date;
    }

    public setDate(date: Date): void {
        this.date = date;
    }
}

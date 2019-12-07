import { IProduct, Product } from './product';

export interface ICategory {
    id: number;
    name: string;
}

export class Category {
    private id: number;
    private name: string;
    constructor(category?: ICategory) {
        if (category) {
            this.id = category.id;
            this.name = category.name;
        }
    }

    public getId(): number {
        return this.id;
    }

    public setId(id: number): void {
        this.id = id;
    }

    public getName(): string {
        return this.name;
    }

    public setName(name: string): void {
        this.name = name;
    }
}

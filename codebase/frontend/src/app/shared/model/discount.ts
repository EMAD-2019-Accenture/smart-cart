import { IProduct, Product } from './product';

export interface IDiscount {
    id: number;
    start: Date;
    end: Date;
    amount: number;
}

export class Discount {
    private id: number;
    private start: Date;
    private end: Date;
    private amount: number;

    constructor(discount?: IDiscount) {
        if (discount) {
            this.id = discount.id;
            this.start = discount.start;
            this.end = discount.end;
            this.amount = discount.amount;
        }
    }

    public getId(): number {
        return this.id;
    }

    public setId(id: number): void {
        this.id = id;
    }

    public getStart(): Date {
        return this.start;
    }

    public setStart(start: Date): void {
        this.start = start;
    }

    public getEnd(): Date {
        return this.end;
    }

    public setEnd(end: Date): void {
        this.end = end;
    }

    public getAmount(): number {
        return this.amount;
    }

    public setAmount(amount: number): void {
        this.amount = amount;
    }
}

import { IProduct, Product } from './product';

export interface IPercentDiscount {
    id: number;
    start: Date;
    end: Date;
    value: number;
}

export class PercentDiscount {
    private id: number;
    private start: Date;
    private end: Date;
    private value: number;

    constructor(percentDiscount?: IPercentDiscount) {
        if (percentDiscount) {
            this.id = percentDiscount.id;
            this.start = percentDiscount.start;
            this.end = percentDiscount.end;
            this.value = percentDiscount.value;
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

    public getValue(): number {
        return this.value;
    }

    public setValue(value: number): void {
        this.value = value;
    }
}

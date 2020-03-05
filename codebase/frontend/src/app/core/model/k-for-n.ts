import { Product, IProduct } from './product';

export class IKForN {
    id: number;
    start: Date;
    end: Date;
    condition: number;
    free: number;
}


export class KForN {
    private id: number;
    private start: Date;
    private end: Date;
    private condition: number;
    private free: number;

    constructor(kForN?: IKForN) {
        if (kForN) {
            this.id = kForN.id;
            this.start = kForN.start;
            this.end = kForN.end;
            this.condition = kForN.condition;
            this.free = kForN.free;
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

    public getCondition(): number {
        return this.condition;
    }

    public setCondition(condition: number): void {
        this.condition = condition;
    }

    public getFree(): number {
        return this.free;
    }

    public setFree(free: number): void {
        this.free = free;
    }
}

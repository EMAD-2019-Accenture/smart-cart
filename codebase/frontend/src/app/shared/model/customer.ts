import { IUser, User } from './user';

export interface ICustomer {
    id: number;
    birth: Date;
    nationality: string;
    vegan: boolean;
    vegetarian: boolean;
    celiac: boolean;
    user: IUser;
}

export class Customer {
    private id: number;
    private birth: Date;
    private nationality: string;
    private vegan: boolean;
    private vegetarian: boolean;
    private celiac: boolean;
    private user: User;

    constructor(customer?: ICustomer) {
        if (customer) {
            this.id = customer.id;
            this.birth = customer.birth;
            this.nationality = customer.nationality;
            this.vegan = customer.vegan;
            this.vegetarian = customer.vegetarian;
            this.celiac = customer.celiac;
            this.user = new User(customer.user);
        }
    }

    public getId(): number {
        return this.id;
    }

    public setId(id: number): void {
        this.id = id;
    }

    public getBirth(): Date {
        return this.birth;
    }

    public setBirth(birth: Date): void {
        this.birth = birth;
    }

    public getNationality(): string {
        return this.nationality;
    }

    public setNationality(nationality: string): void {
        this.nationality = nationality;
    }

    public isVegan(): boolean {
        return this.vegan;
    }

    public setVegan(vegan: boolean): void {
        this.vegan = vegan;
    }

    public isVegetarian(): boolean {
        return this.vegetarian;
    }

    public setVegetarian(vegetarian: boolean): void {
        this.vegetarian = vegetarian;
    }

    public isCeliac(): boolean {
        return this.celiac;
    }

    public setCeliac(celiac: boolean): void {
        this.celiac = celiac;
    }

    public getUser(): User {
        return this.user;
    }

    public setUser(user: User): void {
        this.user = user;
    }
}

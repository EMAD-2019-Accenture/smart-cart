import { Allergen, IAllergen } from './allergen';

export interface IUser {
    id: number;
    login: string;
    firstName: string;
    lastName: string;
    email: string;
    imageUrl: string;
    allergens: IAllergen[];
}

export class User {
    private id: number;
    private login: string;
    private firstName: string;
    private lastName: string;
    private email: string;
    private imageUrl: string;
    private allergens: Array<Allergen>;

    constructor(user?: IUser) {
        this.allergens = new Array<Allergen>();
        if (user) {
            this.id = user.id;
            this.login = user.login;
            this.firstName = user.firstName;
            this.lastName = user.lastName;
            this.email = user.email;
            this.imageUrl = user.imageUrl;
            if (user.allergens != null) {
                user.allergens.forEach(element => {
                    this.allergens.push(new Allergen(element));
                });
            }
        }
    }

    public getId(): number {
        return this.id;
    }

    public setId(id: number): void {
        this.id = id;
    }

    public getLogin(): string {
        return this.login;
    }

    public setLogin(login: string): void {
        this.login = login;
    }

    public getFirstName(): string {
        return this.firstName;
    }

    public setFirstName(firstName: string): void {
        this.firstName = firstName;
    }

    public getLastName(): string {
        return this.lastName;
    }

    public setLastName(lastName: string): void {
        this.lastName = lastName;
    }

    public getEmail(): string {
        return this.email;
    }

    public setEmail(email: string): void {
        this.email = email;
    }

    public getImageUrl(): string {
        return this.imageUrl;
    }

    public setImageUrl(imageUrl: string): void {
        this.imageUrl = imageUrl;
    }

    public getAllergens(): Array<Allergen> {
        return this.allergens;
    }

    public setAllergens(allergens: Array<Allergen>): void {
        this.allergens = allergens;
    }

}

import { IUser, User } from './user';

export interface IAllergen {
    id: number;
    name: string;
    description: string;
    imageUrl: string;
    users: IUser[];
}

export class Allergen {
    private id: number;
    private name: string;
    private description: string;
    private imageUrl: string;
    private users: Array<User>;

    constructor(allergen?: IAllergen) {
        this.users = new Array<User>();
        if (allergen) {
            this.id = allergen.id;
            this.name = allergen.name;
            this.description = allergen.description;
            this.imageUrl = allergen.imageUrl;
            if (allergen.users != null) {
                allergen.users.forEach(element => {
                    this.users.push(new User(element));
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

    public getImageUrl(): string {
        return this.imageUrl;
    }

    public setImageUrl(imageUrl: string): void {
        this.imageUrl = imageUrl;
    }
}

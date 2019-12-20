export interface IUser {
    id: number;
    login: string;
    firstName: string;
    lastName: string;
    email: string;
    imageUrl: string;
}

export class User {
    private id: number;
    private login: string;
    private firstName: string;
    private lastName: string;
    private email: string;
    private imageUrl: string;

    constructor(user?: IUser) {
        if (user) {
            this.id = user.id;
            this.login = user.login;
            this.firstName = user.firstName;
            this.lastName = user.lastName;
            this.email = user.email;
            this.imageUrl = user.imageUrl;
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

}

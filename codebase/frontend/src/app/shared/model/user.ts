export interface IUser {
    id: number;
    login: string;
    password: string;
    firstName: string;
    lastName: string;
    email: string;
    activated: boolean;
}

export class User {
    private id: number;
    private login: string;
    private password: string;
    private firstName: string;
    private lastName: string;
    private email: string;
    private activated: boolean;

    constructor(user?: IUser) {
        if (user) {
            this.id = user.id;
            this.login = user.login;
            this.password = user.password;
            this.firstName = user.firstName;
            this.lastName = user.lastName;
            this.email = user.email;
            this.activated = user.activated;
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

    public getPassword(): string {
        return this.password;
    }

    public setPassword(password: string): void {
        this.password = password;
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

    public isActivated(): boolean {
        return this.activated;
    }

    public setActivated(activated: boolean): void {
        this.activated = activated;
    }
}

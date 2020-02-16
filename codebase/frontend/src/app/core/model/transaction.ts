import { Product, IProduct } from './product';
import { IUser, User } from './user';

export interface ITransaction {
    id: number;
    date: Date;
    user: IUser;
    products: IProduct[];
}

export class Transaction {
    private id: number;
    private date: Date;
    private user: User;
    private products: Array<Product>;

    constructor(transaction?: ITransaction) {
        if (transaction) {
            this.id = transaction.id;
            this.date = transaction.date;
            this.user = new User(transaction.user);
            if (transaction.products != null) {
                transaction.products.forEach(element => {
                    this.products.push(new Product(element));
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

    public getDate(): Date {
        return this.date;
    }

    public setDate(date: Date): void {
        this.date = date;
    }
    
    public getUser(): User {
        return this.user;
    }

    public setUser(user: User): void {
        this.user = user;
    }
}

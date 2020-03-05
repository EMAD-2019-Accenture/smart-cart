import { CartItem, ICartItem } from './cart-item';

export interface ICart {
    id: number;
    active: boolean;
    items: ICartItem[];
}

export class Cart {
    private id: number;
    private active: boolean;
    private items: Array<CartItem>;

    constructor(cart?: ICart) {
        this.active = false;
        this.items = new Array<CartItem>();
        if (cart) {
            this.id = cart.id;
            this.active = cart.active;
            cart.items.forEach(element => {
                this.items.push(new CartItem(element));
            });
        }
    }

    public getId(): number {
        return this.id;
    }
    public setId(id: number) {
        this.id = id;
    }

    public isActive(): boolean {
        return this.active;
    }

    public setActive(active: boolean) {
        this.active = active;
    }

    public getItems(): Array<CartItem> {
        return this.items;
    }

    public setItems(items: Array<CartItem>) {
        this.items = items;
    }
}

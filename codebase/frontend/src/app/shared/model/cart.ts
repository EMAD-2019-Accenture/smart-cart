import { CartItem, ICartItem } from './cart-item';

export interface ICart {
    active: boolean;
    items: ICartItem[];
}

export class Cart {
    private active: boolean;
    private items: Array<CartItem>;

    constructor(cart?: ICart) {
        this.active = false;
        this.items = new Array<CartItem>();
        if (cart) {
            this.active = cart.active;
            cart.items.forEach(element => {
                this.items.push(new CartItem(element));
            });
        }
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


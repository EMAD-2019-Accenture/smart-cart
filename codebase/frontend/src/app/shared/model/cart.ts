import { CartItem } from './cart-item';

export class Cart {
    private active: boolean;
    // Lista di "voci carrello" e non di articoli
    private items: Array<CartItem>;

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


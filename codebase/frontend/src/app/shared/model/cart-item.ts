import { Product } from './product';

export class CartItem {
    product: Product;
    quantity: number;

    public getQuantity(): number {
        return this.quantity;
    }

    public setQuantity(quantity: number) {
        this.quantity = quantity;
    }
}

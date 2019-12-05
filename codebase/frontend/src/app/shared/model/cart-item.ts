import { Product, IProduct } from './product';

export interface ICartItem {
    product: IProduct;
    quantity: number;
}

export class CartItem {
    private product: Product;
    private quantity: number;

    constructor(cartItem?: ICartItem) {
        if (cartItem) {
            this.product = new Product(cartItem.product);
            this.quantity = cartItem.quantity;
        }
    }

    public getProduct(): Product {
        return this.product;
    }

    public setProduct(product: Product): void {
        this.product = product;
    }

    public getQuantity(): number {
        return this.quantity;
    }

    public setQuantity(quantity: number): void {
        this.quantity = quantity;
    }

}

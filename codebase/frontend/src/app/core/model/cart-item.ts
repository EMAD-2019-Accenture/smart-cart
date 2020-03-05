import { IProduct, Product } from './product';

export interface ICartItem {
    id: number;
    product: IProduct;
    quantity: number;
}

export class CartItem {
    private id: number;
    private product: Product;
    private quantity: number;

    constructor(cartItem?: ICartItem) {
        if (cartItem) {
            this.id = cartItem.id;
            this.product = new Product(cartItem.product);
            this.quantity = cartItem.quantity;
        }
    }

    public getId(): number {
        return this.id;
    }
    public setId(id: number) {
        this.id = id;
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

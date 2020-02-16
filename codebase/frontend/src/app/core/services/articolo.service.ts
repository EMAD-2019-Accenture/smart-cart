import { Injectable } from '@angular/core';
import { CartItem } from '../model/cart-item';
import { IProduct, Product } from '../model/product';
import { HttpCommonService } from './http-common.service';

// tslint:disable: align
@Injectable({
  providedIn: 'root',
})
export class ArticoloService {
  // private scanPath = 'http://localhost:8080/api/products/scan/';
  private scanPath = 'https://smart-cart-acenture.herokuapp.com/api/products/scan/';

  constructor(private http: HttpCommonService) { }

  public getProductByBarcode(barcode: number): Promise<IProduct> {
    return this.http.getRequest(this.scanPath + barcode) as Promise<IProduct>;
  }

  public getUnitFullPrice(product: Product): number {
    return product.getPrice();
  }

  public getFullPrice(cartItem: CartItem): number {
    return this.getUnitFullPrice(cartItem.getProduct()) * cartItem.getQuantity();
  }

  public getUnitDiscountedPrice(product: Product): number {
    return product.getDiscountedPrice();
  }

  public getDiscountedPrice(cartItem: CartItem): number {
    return this.getUnitDiscountedPrice(cartItem.getProduct()) * cartItem.getQuantity();
  }

  public increaseQuantity(cartItem: CartItem) {
    cartItem.setQuantity(cartItem.getQuantity() + 1);
  }

  public decreaseQuantity(cartItem: CartItem) {
    if (cartItem.getQuantity() > 1) {
      cartItem.setQuantity(cartItem.getQuantity() - 1);
    }
  }

  public makeCartItem(product: Product): CartItem {
    const cartItem: CartItem = new CartItem();
    cartItem.setProduct(product);
    cartItem.setQuantity(1);
    return cartItem;
  }
}

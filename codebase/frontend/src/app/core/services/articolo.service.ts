import { Injectable } from '@angular/core';
import { CartItem } from '../../shared/model/cart-item';
import { IProduct } from '../../shared/model/product';
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

  public increaseQuantity(cartItem: CartItem) {
    cartItem.setQuantity(cartItem.getQuantity() + 1);
  }

  public decreaseQuantity(cartItem: CartItem) {
    if (cartItem.getQuantity() > 1) {
      cartItem.setQuantity(cartItem.getQuantity() - 1);
    }
  }
}

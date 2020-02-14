import { Injectable } from '@angular/core';
import { HttpCommonService } from '../core/services/http-common.service';
import { ICategory } from '../shared/model/category';
import { IProduct } from '../shared/model/product';

// tslint:disable: align
@Injectable({
  providedIn: 'root',
})
export class CatalogoService {
  // private updateCustomerPath = 'http://localhost:8080/api/categories/';
  // private getProductsPath = 'http://localhost:8080/api/products/';
  private updateCustomerPath = 'https://smart-cart-acenture.herokuapp.com/api/categories/';
  private getProductsPath = 'https://smart-cart-acenture.herokuapp.com/api/products/';

  constructor(private http: HttpCommonService) {
  }

  public getCategories(): Promise<ICategory[]> {
    return this.http.getRequest(this.updateCustomerPath) as Promise<ICategory[]>;
  }

  public getProducts(): Promise<IProduct[]> {
    return this.http.getRequest(this.getProductsPath) as Promise<IProduct[]>;
  }

}

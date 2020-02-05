import { Injectable } from '@angular/core';
import { HttpCommonService } from '../core/services/http-common.service';
import { Category, ICategory } from '../shared/model/category';
import { IProduct, Product } from '../shared/model/product';

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

  public filterByCategory(products: Product[], categories: Category[], selectedIndex: number): Product[] {
    if (selectedIndex > 0) {
      const category: Category = categories[selectedIndex - 1];
      return products.filter(product => product.getCategory().getId() === category.getId());
    }
    return products;
  }

  public filterByKeyword(products: Product[], keyword: string): Product[] {
    const regex = new RegExp('^.*(' + keyword + ').*$', 'i');
    return products.filter(product => regex.test(product.getName()));
  }

  public filterByDiscountCheck(products: Product[], discountCheck: boolean) {
    if (discountCheck) {
      return products.filter(product => {
        const discount: string = JSON.stringify(product.getDiscount());
        // const percentDiscount: string = JSON.stringify(product.getPercentDiscount());
        // const kForN: string = JSON.stringify(product.getKForN());
        return discount !== '{}' /*|| percentDiscount !== '{}' || kForN !== '{}'*/;
      });
    }
    return products;
  }

}

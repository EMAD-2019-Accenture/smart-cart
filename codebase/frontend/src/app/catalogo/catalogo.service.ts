import { HttpCommonService } from '../shared/http-common.service';
import { ICategory } from '../shared/model/category';
import { IProduct } from '../shared/model/product';

// tslint:disable: align
export class CatalogoService {
  private updateCustomerPath = 'http://localhost:8080/api/categories/';
  private getProductsPath = 'http://localhost:8080/api/products/';

  constructor(private http: HttpCommonService) {
  }

  public getCategories(): Promise<ICategory[]> {
    return this.http.getRequest(this.updateCustomerPath) as Promise<ICategory[]>;
  }

  public getProducts(): Promise<IProduct[]> {
    return this.http.getRequest(this.getProductsPath) as Promise<IProduct[]>;
  }
  
}

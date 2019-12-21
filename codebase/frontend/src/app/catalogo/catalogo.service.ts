import { HttpCommonService } from '../shared/http-common.service';
import { Category } from '../shared/model/category';

// tslint:disable: align
export class CatalogoService {
  private updateCustomerPath = 'http://localhost:8080/api/categories/';

  constructor(private http: HttpCommonService) { }

  public getCategories(): Promise<Array<Category>> {
    return this.http.getRequest(this.updateCustomerPath);
  }
}

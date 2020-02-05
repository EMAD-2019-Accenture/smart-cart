import { Injectable } from '@angular/core';
import { HttpCommonService } from '../core/services/http-common.service';
import { Customer, ICustomer } from '../core/model/customer';
import { User } from '../core/model/user';

// tslint:disable: align
@Injectable({
  providedIn: 'root',
})
export class PreferenzeService {
  // private getCustomerByUsernamePath = 'http://localhost:8080/api/customers/logged';
  // private updateCustomerPath = 'http://localhost:8080/api/customers/';
  private getLoggedCustomer = 'https://smart-cart-acenture.herokuapp.com/api/customers/logged';
  private updateCustomerPath = 'https://smart-cart-acenture.herokuapp.com/api/customers/';

  constructor(private http: HttpCommonService) { }

  public makeEmptyCustomer() {
    const customer: Customer = new Customer();
    customer.setUser(new User());
    return customer;
  }

  private fetchCustomerByUserMOCK(token: string): Promise<ICustomer> {
    const iCustomer: ICustomer = {
      id: 3,
      birth: new Date('2019-10-10'),
      nationality: 'Italiana',
      vegan: true,
      vegetarian: false,
      celiac: false,
      user: {
        id: 1,
        login: null,
        firstName: 'pippotto',
        lastName: 'cattaneo',
        email: 'pipp8@unisa.it',
        imageUrl: '',
        allergens: [
          {
            id: 1102,
            name: 'Latte',
            description: 'Latte e prodotti derivati (compreso lattosio)',
            imageUrl: null
          }
        ]
      }
    };
    return new Promise<ICustomer>((resolve, reject) => resolve(iCustomer));
  }

  public async getCustomer(): Promise<ICustomer> {
    return this.http.getRequest(this.getLoggedCustomer) as Promise<ICustomer>;
  }

  public async update(customer: Customer): Promise<ICustomer> {
    const httpBody = JSON.stringify(customer);
    return this.http.putRequest(this.updateCustomerPath, httpBody);
  }
}

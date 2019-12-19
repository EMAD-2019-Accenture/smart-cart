import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from '../auth/auth.service';
import { Customer, ICustomer } from '../shared/model/customer';
import { User } from '../shared/model/user';

// tslint:disable: align
export class PreferenzeService {
  // DEBUG In production must change
  private getCustomerByUsernamePath = 'http://localhost:8080/api/customers/logged';
  private serverHost = 'http://localhost:8100';

  constructor(private http: HttpClient,
    private authService: AuthService) { }

  public makeEmptyCustomer() {
    const customer: Customer = new Customer();
    customer.setUser(new User());
    return customer;
  }

  private makeHttpOptions(token: string) {
    return {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + token,
        'Access-Control-Allow-Origin': this.serverHost,
      })
    };
  }

  private fetchCustomerByUser(token: string): Promise<ICustomer> {
    const httpOptions = this.makeHttpOptions(token);
    return this.http.get(this.getCustomerByUsernamePath, httpOptions).toPromise() as Promise<ICustomer>;
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
        imageUrl: ''
      }
    };
    return new Promise<ICustomer>((resolve, reject) => resolve(iCustomer));
  }

  public async getCustomer(): Promise<ICustomer> {
    const token: string = await this.authService.getAuthToken();
    // TODO change to non MOCK
    return this.fetchCustomerByUser(token);
  }

  public update(customer: Customer) {

  }
}

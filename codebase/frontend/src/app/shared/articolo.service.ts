import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Storage } from '@ionic/storage';
import { Cart } from './model/cart';
import { CartItem } from './model/cart-item';

// tslint:disable: align
export class ArticoloService {

  // DEBUG In production must change
  private scanPath = 'http://localhost:8080/api/products/scan/';
  private serverHost = 'http://localhost:8100';

  constructor(private http: HttpClient,
    private storage: Storage) { }

  // TODO This method should be in AuthService
  private isLoggedIn(): Promise<string> {
    return this.storage.get('ACCESS_TOKEN');
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

  private fetchProductByBarcode(barcode: number, token: string): Promise<any> {
    const httpOptions = this.makeHttpOptions(token);
    return this.http.get(this.scanPath + barcode, httpOptions).toPromise();
  }

  public async getProduct(barcode: number): Promise<object> {
    const token: string = await this.isLoggedIn();
    return this.fetchProductByBarcode(barcode, token);
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

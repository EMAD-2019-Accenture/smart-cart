import { Product } from './model/product';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Storage } from '@ionic/storage';

export class ArticoloService {

  constructor(private http: HttpClient, private storage: Storage) { }

  // Duplicate with ScanService
  private isLoggedIn(): Promise<any> {
    return this.storage.get('ACCESS_TOKEN').catch(() => {
      console.error('Non sei autorizzato');
    });
  }

  // Duplicate with ScanService
  private makeOptions(token: string) {
    return {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + token,
        'Access-Control-Allow-Origin': 'http://localhost:8100' // DEBUG In production must change
      })
    };
  }

  private fetchProductById(id: number, token: string): Promise<any> {
    const options = this.makeOptions(token);
    return this.http.get('http://localhost:8080/api/products/' + id, options).toPromise().catch(() => {
      console.error('Errore nel recupero dell\'articolo');
    });
  }

  public async getProduct(id) {
    const token: string = await this.isLoggedIn();
    const product: Product = await this.fetchProductById(id, token);
    console.log(product);
    return product;
  }
}

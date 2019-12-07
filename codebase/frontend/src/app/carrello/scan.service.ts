import { BarcodeScanner } from '@ionic-native/barcode-scanner/ngx';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Storage } from '@ionic/storage';
import { isDevMode } from '@angular/core';

export class ScanService {

  constructor(private barcodeScanner: BarcodeScanner, private http: HttpClient, private storage: Storage) {
  }

  private isLoggedIn(): Promise<any> {
    return this.storage.get('ACCESS_TOKEN').catch(() => {
      console.error('Non sei autorizzato');
    });
  }

  private makeOptions(token: string) {
    return {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + token,
        'Access-Control-Allow-Origin': 'http://localhost:8100' // DEBUG In production must change
      })
    };
  }

  private fetchProduct(barcode: string, token: string): Promise<any> {
    const options = this.makeOptions(token);
    return this.http.get('http://localhost:8080/api/products/scan/' + barcode, options).toPromise().catch(() => {
      console.error('Errore nella richiesta dell\'articolo');
    });
  }

  // DEBUG
  private mockScan(): string {
    return '8001120783806';
  }

  private scan(): Promise<any> {
    const options = {
      resultDisplayDuration: 0
    };
    return this.barcodeScanner.scan(options).catch(() => {
      console.error('Errore nello scan');
    });
  }

  public async startScan() {
    const token: string = await this.isLoggedIn();
    let barcode: string;
    if (isDevMode()) {
      barcode = this.mockScan();
    } else {
      barcode = await this.scan();
    }
    const product = await this.fetchProduct(barcode, token);
    console.log(product);
  }
}
